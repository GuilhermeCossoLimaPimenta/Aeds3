import java.io.IOException;


public class Pcv {

    int id;
    long pos;
    
    public Pcv() {
    }
    
    public Pcv(int id, long pos)throws IOException{
        this.id = id;
        this.pos = pos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPos() {
        return this.pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", pos='" + pos + "'" +
            "}";
    }
    
    @Override
    public int hashCode() {
        return id;
    }

    public int size(){
        return 12;
    }

    public int sherch(){
        int seekPos=0;
        int id = this.id --;
            if(id != 0)
                seekPos = size()*id;
        return seekPos; 
    }
}
