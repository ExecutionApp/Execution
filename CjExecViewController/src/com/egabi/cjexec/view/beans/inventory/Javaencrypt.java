package com.egabi.cjexec.view.beans.inventory;

public class Javaencrypt {

    /**
     * @param args the command line arguments
     * 
     * 
     * 
     * 
     */
    
//    public static void efinanceMethod() {
//        StringBuilder a = new StringBuilder();
//        char stx = (char)Integer.parseInt("2", 16);
//        char etx = (char)Integer.parseInt("3", 16);
//        a.append(stx);
//        a.append("1,8987767,818,000001,000000");
//        a.append(etx);
//        
//        byte[] buffer = encryptDecryptXor(a.toString().getBytes(), a.toString().length());
//        System.out.println("buffer:::::::   " + new String(buffer));
//        
//    }
    
   public byte[] encryptDecryptXor(byte[] buff, int bSize){
        byte[] buffer = buff;
        int blockSize = 3;
        byte[] key = "Yamany".getBytes();
        for(int i = 0, y = 0; i<= bSize;){
            for(int o = 0; o <= blockSize; o++){
                if(i<bSize){
                    buffer[i] = (byte)(buffer[i] ^ key[y]);
                }
                i++;
            }
            y++;
            if(y==key.length){
                y = 0;
            }
        }
        
        return buffer;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
//        efinanceMethod();
        
        
        
        
        
    }
    
}
 