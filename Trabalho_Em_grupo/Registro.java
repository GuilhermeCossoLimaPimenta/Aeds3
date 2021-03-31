public interface Registro {
    public void setId(int ID) throws java.io.IOException;
    public int getId() throws java.io.IOException;
    public byte[] toByteArray() throws java.io.IOException; 
    public void fromByteArray(byte[] ba) throws java.io.IOException;
  
}
