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

// -------------------------------------------



    val io = IO(new Bundle {
        val vec_b   = Input(Vec(vec_len, SInt(bw.W))) // north
        val vec_x   = Input(Vec(vec_len, SInt(bw.W))) // west
        val vec_out = Output(Vec(vec_len, SInt(bw.W))) // output of the array
        val weights = Input(Vec(rows, Vec(cols, SInt(bw.W))))

        val compute_enable = Input(Bool()) 
        val weights_are_loaded = Output(Bool())
        val fake_output = Output(SInt(bw.W))
    })

  

    // Internal Registers and Wires
    //
    val pes = Seq.fill(rows, cols)(Module(new MAC(bw,bw,bw))) 
    val weights_loaded = RegInit(false.B)
    val output_buf = RegInit(VecInit (Seq.fill(vec_len) (0.S(bw.W))))



    /* Initialize all signals of the MAC units */ 
      pes.map(_.map(initializeMac)) 
      (io.vec_out, output_buf).zipped.map( _ := _ ) 

      
    io.weights_are_loaded := weights_loaded


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

    
     
    // Now that weights are loaded, we can do some computation

  when (weights_loaded) 
  {

    pes(0)(0).io.north.valid := true.B
    pes(0)(0).io.west.valid := true.B
    pes(0)(0).io.north.bits := 1.S(bw.W) 
    pes(0)(0).io.west.bits := 1.S(bw.W) 

  } 

  io.fake_output := pes(0)(0).io.south.bits


















    



} 
