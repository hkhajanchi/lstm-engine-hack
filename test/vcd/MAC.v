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
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
`endif // RANDOMIZE_REG_INIT
  reg  busy; // @[MAC.scala 29:23]
  reg  comp_done; // @[MAC.scala 30:28]
  reg  weight_loaded; // @[MAC.scala 31:32]
  reg [15:0] weight; // @[MAC.scala 34:25]
  reg [15:0] east_reg; // @[MAC.scala 35:27]
  reg [15:0] north_reg; // @[MAC.scala 36:28]
  reg [31:0] mac_buf; // @[MAC.scala 37:26]
  wire  _T = ~weight_loaded; // @[MAC.scala 40:30]
  wire  _T_1 = ~busy; // @[MAC.scala 41:23]
  wire  _T_4 = io_weight_input_valid & _T; // @[MAC.scala 53:33]
  wire  _GEN_1 = _T_4 | weight_loaded; // @[MAC.scala 53:52]
  wire  _T_5 = io_west_valid & io_north_valid; // @[MAC.scala 63:25]
  wire  _T_7 = _T_5 & _T_1; // @[MAC.scala 63:43]
  wire  _T_8 = _T_7 & weight_loaded; // @[MAC.scala 63:52]
  wire  _GEN_2 = _T_8 | busy; // @[MAC.scala 63:70]
  wire [31:0] _T_9 = $signed(east_reg) * $signed(weight); // @[MAC.scala 78:28]
  wire [31:0] _GEN_12 = {{16{north_reg[15]}},north_reg}; // @[MAC.scala 78:38]
  wire [31:0] _T_12 = $signed(_T_9) + $signed(_GEN_12); // @[MAC.scala 78:38]
  wire  _GEN_8 = busy | comp_done; // @[MAC.scala 74:18]
  wire  _T_14 = _T_1 & comp_done; // @[MAC.scala 90:15]
  assign io_west_ready = _T_8 ? 1'h0 : _T_1; // @[MAC.scala 41:20 MAC.scala 66:21]
  assign io_north_ready = _T_8 ? 1'h0 : _T_1; // @[MAC.scala 42:20 MAC.scala 67:22]
  assign io_south_valid = _T_1 & comp_done; // @[MAC.scala 45:20 MAC.scala 94:23]
  assign io_south_bits = mac_buf; // @[MAC.scala 48:19 MAC.scala 88:18]
  assign io_east_valid = _T_1 & comp_done; // @[MAC.scala 44:20 MAC.scala 93:22]
  assign io_east_bits = east_reg; // @[MAC.scala 47:19 MAC.scala 87:17]
  assign io_weight_input_ready = ~weight_loaded; // @[MAC.scala 40:27]
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
  busy = _RAND_0[0:0];
  _RAND_1 = {1{`RANDOM}};
  comp_done = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  weight_loaded = _RAND_2[0:0];
  _RAND_3 = {1{`RANDOM}};
  weight = _RAND_3[15:0];
  _RAND_4 = {1{`RANDOM}};
  east_reg = _RAND_4[15:0];
  _RAND_5 = {1{`RANDOM}};
  north_reg = _RAND_5[15:0];
  _RAND_6 = {1{`RANDOM}};
  mac_buf = _RAND_6[31:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      busy <= 1'h0;
    end else if (busy) begin
      busy <= 1'h0;
    end else begin
      busy <= _GEN_2;
    end
    if (reset) begin
      comp_done <= 1'h0;
    end else if (_T_14) begin
      comp_done <= 1'h0;
    end else begin
      comp_done <= _GEN_8;
    end
    if (reset) begin
      weight_loaded <= 1'h0;
    end else begin
      weight_loaded <= _GEN_1;
    end
    if (reset) begin
      weight <= 16'sh0;
    end else if (_T_4) begin
      weight <= io_weight_input_bits;
    end
    if (reset) begin
      east_reg <= 16'sh0;
    end else if (_T_8) begin
      east_reg <= io_west_bits;
    end
    if (reset) begin
      north_reg <= 16'sh0;
    end else if (_T_8) begin
      north_reg <= io_north_bits;
    end
    if (reset) begin
      mac_buf <= 32'sh0;
    end else if (busy) begin
      mac_buf <= _T_12;
    end
  end
endmodule
