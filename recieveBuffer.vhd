library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use IEEE.std_logic_unsigned.all;
entity recieveBuffer is
port
(
    rst:in std_logic;
    clk:in std_logic;
    fifowrite:in std_logic;
    fiforead:in std_logic;
    start:in std_logic;
    datasin:in std_logic_vector(15 downto 0);
    valid:out std_logic;
    datasout:out std_logic_vector(15 downto 0);
    finished:out std_logic
);
end recieveBuffer;
architecture Arc_recieveBuffer of recieveBuffer is
type FiFoMem is array(0 to 15) of std_logic_vector(15 downto 0);
signal FiFo:FiFoMem:=(("0000000000000000"),("0000000000000000"),("0000000000000000"),("0000000000000000"),
              ("0000000000000000"),("0000000000000000"),("0000000000000000"),("0000000000000000"),
              ("0000000000000000"),("0000000000000000"),("0000000000000000"),("0000000000000000"),
              ("0000000000000000"),("0000000000000000"),("0000000000000000"),("0000000000000000"));
signal head:integer range 0 to 16:=0;
signal tail:integer range 0 to 16:=0;
signal readtag:std_logic:='0';
begin
p1:process(rst,clk)
    begin
      if  A = '1' then
          is_decoder_next <= IN_s010;
          merror <= '0';
          cerror <= '0';
      else
          sample8_next := resize(sample8, 9) + to_unsigned(16#001#, 9);
          merror <= '0';
          cerror <= '0';
      end if;
   end process p1;
end Arc_recieveBuffer;
