package arch.sm213.machine.student;

import machine.AbstractMainMemory;


/**
 * Main Memory of Simple CPU.
 *
 * Provides an abstraction of main memory (DRAM).
 */

public class MainMemory extends AbstractMainMemory {
  private byte [] mem;
  
  /**
   * Allocate memory.
   * @param byteCapacity size of memory in bytes.
   */
  public MainMemory (int byteCapacity) {
    mem = new byte [byteCapacity];
  }
  
  /**
   * Determine whether an address is aligned to specified length.
   * @param address memory address.
   * @param length byte length.
   * @return true iff address is aligned to length.
   */
  @Override public boolean isAccessAligned (int address, int length) {
    return address % length == 0;
  }
  
  /**
   * Convert an sequence of four bytes into a Big Endian integer.
   * @param byteAtAddrPlus0 value of byte with lowest memory address (base address).
   * @param byteAtAddrPlus1 value of byte at base address plus 1.
   * @param byteAtAddrPlus2 value of byte at base address plus 2.
   * @param byteAtAddrPlus3 value of byte at base address plus 3 (highest memory address).
   * @return Big Endian integer formed by these four bytes.
   */
  @Override public int bytesToInteger (byte byteAtAddrPlus0, byte byteAtAddrPlus1, byte byteAtAddrPlus2, byte byteAtAddrPlus3) {
    byte[] arr = {byteAtAddrPlus0, byteAtAddrPlus1, byteAtAddrPlus2, byteAtAddrPlus3};

    int value = 0;

    for (int i = 0; i < 4; i++) {
      value = value + (((int) arr[i] << (24 - i*8)) & (0x000000ff << (24 - i*8)));
    }
    return value;
  }
  
  /**
   * Convert a Big Endian integer into an array of 4 bytes organized by memory address.
   * @param  i an Big Endian integer.
   * @return an array of byte where [0] is value of low-address byte of the number etc.
   */
  @Override public byte[] integerToBytes (int i) {
    byte byte1 = (byte) ((i & 0xff000000) >> 24);
    byte byte2 = (byte) ((i & 0x00ff0000) >>16);
    byte byte3 = (byte) ((i & 0x0000ff00) >> 8);
    byte byte4 = (byte) ((i & 0x000000ff));
    byte[] arr = {byte1, byte2, byte3, byte4};
    return arr;
  }
  
  /**
   * Fetch a sequence of bytes from memory.
   * @param address address of the first byte to fetch.
   * @param length  number of bytes to fetch.
   * @throws InvalidAddressException  if any address in the range address to address+length-1 is invalid.
   * @return an array of byte where [0] is memory value at address, [1] is memory value at address+1 etc.
   */
  @Override public byte[] get (int address, int length) throws InvalidAddressException {
    if (!this.isAccessAligned(address, length) || address + length - 1 > this.length() - 1 || address < 0) {
      throw new InvalidAddressException();
    }

    byte[] arr = new byte[length];

    for (int i = 0; i < length; i++) {
      arr[i] = this.mem[address + i];
    }

    return arr;
  }
  
  /**
   * Store a sequence of bytes into memory.
   * @param  address                  address of the first byte in memory to recieve the specified value.
   * @param  value                    an array of byte values to store in memory at the specified address.
   * @throws InvalidAddressException  if any address in the range address to address+value.length-1 is invalid.
   */
  @Override public void set (int address, byte[] value) throws InvalidAddressException {
      if (!this.isAccessAligned(address, value.length) || address + value.length - 1 > this.length() - 1 || address < 0) {
        throw new InvalidAddressException();
      }

      for (byte b : value) {
        this.mem[address] = b;
        address ++;
    }
  }
  
  /**
   * Determine the size of memory.
   * @return the number of bytes allocated to this memory.
   */
  @Override public int length () {
    return mem.length;
  }
}
