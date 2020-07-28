module MAC(
  input         clock,
  input         reset,
  output        io_west_ready,
  input         io_west_valid,
  input  [15:0] io_west_bits,
  output        io_north_ready,
  input         io_north_valid,
  input  [15:0] io_north_bits,
  input         io_south_ready,
  output        io_south_valid,
  output [31:0] io_south_bits,
  input         io_east_ready,
  output        io_east_valid,
  output [15:0] io_east_bits,
  output        io_weight_input_ready,
  input         io_weight_input_valid,
  input  [15:0] io_weight_input_bits
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] weight; // @[MAC.scala 29:25]
  reg  busy; // @[MAC.scala 30:23]
  reg  weight_loaded; // @[MAC.scala 31:32]
  reg [31:0] x; // @[MAC.scala 32:20]
  reg [15:0] east_reg; // @[MAC.scala 33:27]
  wire  _T = ~busy; // @[MAC.scala 36:30]
  wire  _T_3 = ~weight_loaded; // @[MAC.scala 49:36]
  wire  _T_4 = io_weight_input_valid & _T_3; // @[MAC.scala 49:33]
  wire  _GEN_1 = _T_4 | weight_loaded; // @[MAC.scala 49:52]
  wire  _T_5 = io_west_valid & io_north_valid; // @[MAC.scala 59:25]
  wire  _T_7 = _T_5 & _T; // @[MAC.scala 59:43]
  wire  _T_8 = _T_7 & weight_loaded; // @[MAC.scala 59:52]
  wire  _GEN_2 = _T_8 | busy; // @[MAC.scala 59:70]
  wire [31:0] _T_9 = $signed(io_west_bits) * $signed(weight); // @[MAC.scala 68:26]
  wire [31:0] _GEN_6 = {{16{io_north_bits[15]}},io_north_bits}; // @[MAC.scala 68:36]
  wire [31:0] _T_12 = $signed(_T_9) + $signed(_GEN_6); // @[MAC.scala 68:36]
  wire  _T_13 = io_east_valid & io_east_ready; // @[MAC.scala 80:25]
  wire  _T_14 = io_south_valid & io_south_ready; // @[MAC.scala 80:62]
  wire  _T_15 = _T_13 & _T_14; // @[MAC.scala 80:43]
  assign io_west_ready = ~busy; // @[MAC.scala 37:20]
  assign io_north_ready = ~busy; // @[MAC.scala 38:20]
  assign io_south_valid = busy; // @[MAC.scala 41:20 MAC.scala 77:19]
  assign io_south_bits = x; // @[MAC.scala 44:19 MAC.scala 74:18]
  assign io_east_valid = busy; // @[MAC.scala 40:20 MAC.scala 76:18]
  assign io_east_bits = east_reg; // @[MAC.scala 43:19 MAC.scala 73:17]
  assign io_weight_input_ready = ~busy; // @[MAC.scala 36:27]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  weight = _RAND_0[15:0];
  _RAND_1 = {1{`RANDOM}};
  busy = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  weight_loaded = _RAND_2[0:0];
  _RAND_3 = {1{`RANDOM}};
  x = _RAND_3[31:0];
  _RAND_4 = {1{`RANDOM}};
  east_reg = _RAND_4[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      weight <= 16'sh0;
    end else if (_T_4) begin
      weight <= io_weight_input_bits;
    end
    if (reset) begin
      busy <= 1'h0;
    end else if (_T_15) begin
      busy <= 1'h0;
    end else begin
      busy <= _GEN_2;
    end
    if (reset) begin
      weight_loaded <= 1'h0;
    end else begin
      weight_loaded <= _GEN_1;
    end
    if (reset) begin
      x <= 32'sh0;
    end else if (busy) begin
      x <= _T_12;
    end
    if (reset) begin
      east_reg <= 16'sh0;
    end else if (busy) begin
      east_reg <= io_west_bits;
    end
  end
endmodule
