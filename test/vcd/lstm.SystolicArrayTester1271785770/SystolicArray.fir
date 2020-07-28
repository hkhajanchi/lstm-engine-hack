;buildInfoPackage: chisel3, version: 3.3.1, scalaVersion: 2.12.11, sbtVersion: 1.3.10
circuit SystolicArray : 
  module MAC : 
    input clock : Clock
    input reset : Reset
    output io : {flip west : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, flip north : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, south : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, east : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, flip weight_input : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}}
    
    io.weight_input.ready <= UInt<1>("h01") @[MAC.scala 27:27]
    reg weight : SInt<16>, clock with : (reset => (reset, asSInt(UInt<16>("h00")))) @[MAC.scala 29:25]
    io.west.ready <= UInt<1>("h01") @[MAC.scala 31:20]
    io.north.ready <= UInt<1>("h01") @[MAC.scala 32:20]
    io.east.valid <= UInt<1>("h00") @[MAC.scala 34:20]
    io.south.valid <= UInt<1>("h00") @[MAC.scala 35:20]
    io.east.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 37:19]
    io.south.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 38:19]
    when io.weight_input.valid : @[MAC.scala 41:34]
      weight <= io.weight_input.bits @[MAC.scala 42:14]
      io.weight_input.ready <= UInt<1>("h00") @[MAC.scala 43:29]
      skip @[MAC.scala 41:34]
    node _T = and(io.west.valid, io.north.valid) @[MAC.scala 47:25]
    when _T : @[MAC.scala 47:44]
      node _T_1 = mul(io.west.bits, weight) @[MAC.scala 50:43]
      reg _T_2 : SInt, clock @[MAC.scala 50:29]
      _T_2 <= _T_1 @[MAC.scala 50:29]
      reg _T_3 : SInt, clock @[MAC.scala 51:29]
      _T_3 <= io.west.bits @[MAC.scala 51:29]
      node _T_4 = add(_T_2, io.north.bits) @[MAC.scala 53:26]
      node _T_5 = tail(_T_4, 1) @[MAC.scala 53:26]
      node _T_6 = asSInt(_T_5) @[MAC.scala 53:26]
      io.south.bits <= _T_6 @[MAC.scala 53:21]
      io.east.bits <= _T_3 @[MAC.scala 54:21]
      io.south.valid <= UInt<1>("h01") @[MAC.scala 56:22]
      io.east.valid <= UInt<1>("h01") @[MAC.scala 57:22]
      io.west.ready <= UInt<1>("h00") @[MAC.scala 59:22]
      io.north.ready <= UInt<1>("h00") @[MAC.scala 60:22]
      skip @[MAC.scala 47:44]
    
  module MAC_1 : 
    input clock : Clock
    input reset : Reset
    output io : {flip west : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, flip north : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, south : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, east : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, flip weight_input : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}}
    
    io.weight_input.ready <= UInt<1>("h01") @[MAC.scala 27:27]
    reg weight : SInt<16>, clock with : (reset => (reset, asSInt(UInt<16>("h00")))) @[MAC.scala 29:25]
    io.west.ready <= UInt<1>("h01") @[MAC.scala 31:20]
    io.north.ready <= UInt<1>("h01") @[MAC.scala 32:20]
    io.east.valid <= UInt<1>("h00") @[MAC.scala 34:20]
    io.south.valid <= UInt<1>("h00") @[MAC.scala 35:20]
    io.east.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 37:19]
    io.south.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 38:19]
    when io.weight_input.valid : @[MAC.scala 41:34]
      weight <= io.weight_input.bits @[MAC.scala 42:14]
      io.weight_input.ready <= UInt<1>("h00") @[MAC.scala 43:29]
      skip @[MAC.scala 41:34]
    node _T = and(io.west.valid, io.north.valid) @[MAC.scala 47:25]
    when _T : @[MAC.scala 47:44]
      node _T_1 = mul(io.west.bits, weight) @[MAC.scala 50:43]
      reg _T_2 : SInt, clock @[MAC.scala 50:29]
      _T_2 <= _T_1 @[MAC.scala 50:29]
      reg _T_3 : SInt, clock @[MAC.scala 51:29]
      _T_3 <= io.west.bits @[MAC.scala 51:29]
      node _T_4 = add(_T_2, io.north.bits) @[MAC.scala 53:26]
      node _T_5 = tail(_T_4, 1) @[MAC.scala 53:26]
      node _T_6 = asSInt(_T_5) @[MAC.scala 53:26]
      io.south.bits <= _T_6 @[MAC.scala 53:21]
      io.east.bits <= _T_3 @[MAC.scala 54:21]
      io.south.valid <= UInt<1>("h01") @[MAC.scala 56:22]
      io.east.valid <= UInt<1>("h01") @[MAC.scala 57:22]
      io.west.ready <= UInt<1>("h00") @[MAC.scala 59:22]
      io.north.ready <= UInt<1>("h00") @[MAC.scala 60:22]
      skip @[MAC.scala 47:44]
    
  module MAC_2 : 
    input clock : Clock
    input reset : Reset
    output io : {flip west : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, flip north : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, south : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, east : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, flip weight_input : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}}
    
    io.weight_input.ready <= UInt<1>("h01") @[MAC.scala 27:27]
    reg weight : SInt<16>, clock with : (reset => (reset, asSInt(UInt<16>("h00")))) @[MAC.scala 29:25]
    io.west.ready <= UInt<1>("h01") @[MAC.scala 31:20]
    io.north.ready <= UInt<1>("h01") @[MAC.scala 32:20]
    io.east.valid <= UInt<1>("h00") @[MAC.scala 34:20]
    io.south.valid <= UInt<1>("h00") @[MAC.scala 35:20]
    io.east.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 37:19]
    io.south.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 38:19]
    when io.weight_input.valid : @[MAC.scala 41:34]
      weight <= io.weight_input.bits @[MAC.scala 42:14]
      io.weight_input.ready <= UInt<1>("h00") @[MAC.scala 43:29]
      skip @[MAC.scala 41:34]
    node _T = and(io.west.valid, io.north.valid) @[MAC.scala 47:25]
    when _T : @[MAC.scala 47:44]
      node _T_1 = mul(io.west.bits, weight) @[MAC.scala 50:43]
      reg _T_2 : SInt, clock @[MAC.scala 50:29]
      _T_2 <= _T_1 @[MAC.scala 50:29]
      reg _T_3 : SInt, clock @[MAC.scala 51:29]
      _T_3 <= io.west.bits @[MAC.scala 51:29]
      node _T_4 = add(_T_2, io.north.bits) @[MAC.scala 53:26]
      node _T_5 = tail(_T_4, 1) @[MAC.scala 53:26]
      node _T_6 = asSInt(_T_5) @[MAC.scala 53:26]
      io.south.bits <= _T_6 @[MAC.scala 53:21]
      io.east.bits <= _T_3 @[MAC.scala 54:21]
      io.south.valid <= UInt<1>("h01") @[MAC.scala 56:22]
      io.east.valid <= UInt<1>("h01") @[MAC.scala 57:22]
      io.west.ready <= UInt<1>("h00") @[MAC.scala 59:22]
      io.north.ready <= UInt<1>("h00") @[MAC.scala 60:22]
      skip @[MAC.scala 47:44]
    
  module MAC_3 : 
    input clock : Clock
    input reset : Reset
    output io : {flip west : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, flip north : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}, south : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, east : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt}, flip weight_input : {flip ready : UInt<1>, valid : UInt<1>, bits : SInt<16>}}
    
    io.weight_input.ready <= UInt<1>("h01") @[MAC.scala 27:27]
    reg weight : SInt<16>, clock with : (reset => (reset, asSInt(UInt<16>("h00")))) @[MAC.scala 29:25]
    io.west.ready <= UInt<1>("h01") @[MAC.scala 31:20]
    io.north.ready <= UInt<1>("h01") @[MAC.scala 32:20]
    io.east.valid <= UInt<1>("h00") @[MAC.scala 34:20]
    io.south.valid <= UInt<1>("h00") @[MAC.scala 35:20]
    io.east.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 37:19]
    io.south.bits <= asSInt(UInt<1>("h00")) @[MAC.scala 38:19]
    when io.weight_input.valid : @[MAC.scala 41:34]
      weight <= io.weight_input.bits @[MAC.scala 42:14]
      io.weight_input.ready <= UInt<1>("h00") @[MAC.scala 43:29]
      skip @[MAC.scala 41:34]
    node _T = and(io.west.valid, io.north.valid) @[MAC.scala 47:25]
    when _T : @[MAC.scala 47:44]
      node _T_1 = mul(io.west.bits, weight) @[MAC.scala 50:43]
      reg _T_2 : SInt, clock @[MAC.scala 50:29]
      _T_2 <= _T_1 @[MAC.scala 50:29]
      reg _T_3 : SInt, clock @[MAC.scala 51:29]
      _T_3 <= io.west.bits @[MAC.scala 51:29]
      node _T_4 = add(_T_2, io.north.bits) @[MAC.scala 53:26]
      node _T_5 = tail(_T_4, 1) @[MAC.scala 53:26]
      node _T_6 = asSInt(_T_5) @[MAC.scala 53:26]
      io.south.bits <= _T_6 @[MAC.scala 53:21]
      io.east.bits <= _T_3 @[MAC.scala 54:21]
      io.south.valid <= UInt<1>("h01") @[MAC.scala 56:22]
      io.east.valid <= UInt<1>("h01") @[MAC.scala 57:22]
      io.west.ready <= UInt<1>("h00") @[MAC.scala 59:22]
      io.north.ready <= UInt<1>("h00") @[MAC.scala 60:22]
      skip @[MAC.scala 47:44]
    
  module SystolicArray : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip vec_b : SInt<16>[2], flip vec_x : SInt<16>[2], vec_out : SInt<16>[2], flip weights : SInt<16>[2][2]}
    
    inst pes_0_0 of MAC @[SystolicArray.scala 28:44]
    pes_0_0.clock <= clock
    pes_0_0.reset <= reset
    inst pes_0_1 of MAC_1 @[SystolicArray.scala 28:44]
    pes_0_1.clock <= clock
    pes_0_1.reset <= reset
    inst pes_1_0 of MAC_2 @[SystolicArray.scala 28:44]
    pes_1_0.clock <= clock
    pes_1_0.reset <= reset
    inst pes_1_1 of MAC_3 @[SystolicArray.scala 28:44]
    pes_1_1.clock <= clock
    pes_1_1.reset <= reset
    pes_0_0.io.weight_input.valid <= UInt<1>("h01") @[SystolicArray.scala 33:41]
    pes_0_0.io.weight_input.bits <= io.weights[0][0] @[SystolicArray.scala 34:40]
    pes_0_1.io.weight_input.valid <= UInt<1>("h01") @[SystolicArray.scala 33:41]
    pes_0_1.io.weight_input.bits <= io.weights[0][1] @[SystolicArray.scala 34:40]
    pes_1_0.io.weight_input.valid <= UInt<1>("h01") @[SystolicArray.scala 33:41]
    pes_1_0.io.weight_input.bits <= io.weights[1][0] @[SystolicArray.scala 34:40]
    pes_1_1.io.weight_input.valid <= UInt<1>("h01") @[SystolicArray.scala 33:41]
    pes_1_1.io.weight_input.bits <= io.weights[1][1] @[SystolicArray.scala 34:40]
    node _T = eq(pes_1_1.io.weight_input.ready, UInt<1>("h00")) @[SystolicArray.scala 38:53]
    when _T : @[SystolicArray.scala 38:66]
      pes_0_0.io.north.bits <= io.vec_b[0] @[SystolicArray.scala 41:45]
      pes_0_1.io.north.bits <= io.vec_b[0] @[SystolicArray.scala 41:45]
      pes_0_0.io.north.valid <= UInt<1>("h01") @[SystolicArray.scala 42:46]
      pes_0_1.io.north.valid <= UInt<1>("h01") @[SystolicArray.scala 42:46]
      pes_0_0.io.north.bits <= io.vec_b[1] @[SystolicArray.scala 41:45]
      pes_0_1.io.north.bits <= io.vec_b[1] @[SystolicArray.scala 41:45]
      pes_0_0.io.north.valid <= UInt<1>("h01") @[SystolicArray.scala 42:46]
      pes_0_1.io.north.valid <= UInt<1>("h01") @[SystolicArray.scala 42:46]
      pes_0_0.io.west.bits <= io.vec_x[0] @[SystolicArray.scala 46:46]
      pes_1_0.io.west.bits <= io.vec_x[0] @[SystolicArray.scala 46:46]
      pes_0_0.io.west.valid <= UInt<1>("h01") @[SystolicArray.scala 47:47]
      pes_1_0.io.west.valid <= UInt<1>("h01") @[SystolicArray.scala 47:47]
      pes_0_0.io.west.bits <= io.vec_x[1] @[SystolicArray.scala 46:46]
      pes_1_0.io.west.bits <= io.vec_x[1] @[SystolicArray.scala 46:46]
      pes_0_0.io.west.valid <= UInt<1>("h01") @[SystolicArray.scala 47:47]
      pes_1_0.io.west.valid <= UInt<1>("h01") @[SystolicArray.scala 47:47]
      pes_0_1.io.west.bits <= pes_0_0.io.east.bits @[SystolicArray.scala 53:34]
      pes_0_1.io.west.valid <= pes_0_0.io.east.valid @[SystolicArray.scala 54:35]
      pes_1_1.io.west.bits <= pes_1_0.io.east.bits @[SystolicArray.scala 53:34]
      pes_1_1.io.west.valid <= pes_1_0.io.east.valid @[SystolicArray.scala 54:35]
      pes_1_0.io.north.bits <= pes_0_0.io.south.bits @[SystolicArray.scala 62:35]
      pes_1_0.io.north.valid <= pes_0_0.io.south.valid @[SystolicArray.scala 63:36]
      pes_1_1.io.north.bits <= pes_0_1.io.south.bits @[SystolicArray.scala 62:35]
      pes_1_1.io.north.valid <= pes_0_1.io.south.valid @[SystolicArray.scala 63:36]
      when pes_1_0.io.south.valid : @[SystolicArray.scala 69:46]
        io.vec_out[0] <= pes_1_0.io.south.bits @[SystolicArray.scala 70:25]
        skip @[SystolicArray.scala 69:46]
      when pes_1_1.io.south.valid : @[SystolicArray.scala 69:46]
        io.vec_out[1] <= pes_1_1.io.south.bits @[SystolicArray.scala 70:25]
        skip @[SystolicArray.scala 69:46]
      skip @[SystolicArray.scala 38:66]
    
