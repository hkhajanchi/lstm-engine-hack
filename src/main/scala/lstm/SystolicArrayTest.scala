package lstm 

import chisel3._ 

/* 
 *  Does a SAXPY computation using a systolic array 
 *
 *  @input vec_b : bias vector of size vec_len 
 *  @input vec_x : input vector of size vec_len
 *  @input weights: 
 * 
 *
 * Process 1: Load the Weights in 
 *
 *   - all MAC units should have weight_input_ready === 0 
 *   - if (all MAC.weight_input.ready == 0 ) weights_loaded <= 1 
 *
 */ 

class SystolicArrayNew (val rows:Int, val cols:Int, val vec_len:Int, val bw:Int) extends Module {

 


  /* All helper functions here */ 

  def initializeMac (mac: MAC) : Unit = 
  { 
    mac.io.weight_input.valid:= false.B
    mac.io.weight_input.bits := 0.S

    mac.io.north.valid  := false.B
    mac.io.north.bits := 0.S 

    mac.io.west.valid := false.B 
    mac.io.west.bits := 0.S

    mac.io.east.ready := false.B
    mac.io.south.ready := false.B
  }

  def connectMACs_ew (mac_0:MAC, mac_1:MAC) : Unit = 
  {
    // This function wires up two MACs together in the west to east fashion
    mac_1.io.west.valid := mac_0.io.east.valid 
    mac_1.io.west.bits := mac_0.io.east.bits
  } 
  
  def connectMACs_ns (mac_0:MAC, mac_1:MAC) : Unit = 
  {
    // Connects the north & south ports of two MAC units 
    
    mac_1.io.north.valid := mac_0.io.south.valid 
    mac_1.io.north.bits := mac_0.io.south.bits 
  
  } 

// -------------------------------------------



    val io = IO(new Bundle {
        val vec_b   = Input(Vec(vec_len, SInt(bw.W))) // north
        val vec_x   = Input(Vec(vec_len, SInt(bw.W))) // west
        val weights = Input(Vec(rows, Vec(cols, SInt(bw.W))))
        val compute_enable = Input(Bool())

        val vec_out = Output(Vec(vec_len, SInt(bw.W))) // output of the array
        val output_valid = Output(Bool())
    })

  

    // Internal Registers and Wires
    //
    val pes = Seq.fill(rows, cols)(Module(new MAC(bw,bw,bw))) 
    val weights_loaded = RegInit(false.B)
    val output_buf = RegInit(VecInit (Seq.fill(vec_len) (0.S(bw.W))))




 /* --------- Initialize all signals and wire up stuff  */

      pes.map(_.map(initializeMac)) //initializes all MAC signals

      (io.vec_out, output_buf).zipped.map( _ := _ ) // output wires connected to the array's output registers  

     io.output_valid := false.B 

      // Wire up west <> east ports 

      for (i <- 0 until rows) {
        for (j <- 1 until cols) {

          pes(i)(j).io.west.bits := pes(i)(j-1).io.east.bits 
          pes(i)(j).io.west.valid := pes(i)(j-1).io.east.valid 
        } 
      } 

      // wire up north <> south ports 
      
      for (i <- 1 until rows) {
        for (j <- 0 until cols) {

          pes(i)(j).io.north.bits := pes(i-1)(j).io.south.bits 
          pes(i)(j).io.north.valid := pes(i-1)(j).io.south.valid 
        } 
      }

     


/* ------------- Computation Logic ----------- */ 

    // Execute Process 1 - Load Weights

     when(!weights_loaded) {
      
       // maybe replace this with some idiomatic scala expression..
       for (i <- 0 until rows) 
       {
         for (j <- 0 until cols) 
         {
           pes(i)(j).io.weight_input.bits := io.weights(i)(j) 
           }
         }

       pes.map(_.map( e => e.io.weight_input.valid := true.B) ) 
       weights_loaded := true.B
   
       }

    
     
 // Do computation - the MAC tiles within the array should propogate signals automatically after the west-most column and north-most row are fed data 
  
 val comp_done = RegInit(false.B) 

  when (weights_loaded && io.compute_enable) 
  {
    
    // Feed the north vector into the top row 
    
    for (i <- 0 until vec_len) {
      pes(0)(i).io.north.bits := io.vec_b(i) 
      pes(0)(i).io.north.valid := true.B 
    } 


    // Feed the west vector into the first column
    for (i <- 0 until vec_len) {
      pes(i)(0).io.west.bits := io.vec_x(i)
      pes(i)(0).io.west.valid := true.B
    } 

  // make sure the last row is completely valid - not sure if this will syntactically work
 // comp_done :=  pes(rows).reduceLeft( _.io.south.valid && _.io.south.valid) 
 //
 //
  comp_done := true.B 

  } 

 // Latch outputs   

 when (comp_done) {

   for (i <- 0 until vec_len) 
    {
      output_buf(i) := pes(rows-1)(i).io.south.bits 
      comp_done := false.B 
    } 
 } 



} 
