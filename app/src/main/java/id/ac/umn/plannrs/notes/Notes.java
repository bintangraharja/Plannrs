package id.ac.umn.plannrs.notes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblNotes")
public class Notes implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idNotes")
    private int idNotes;
    @ColumnInfo(name = "titleNotes")
    private String titleNotes;
    @ColumnInfo(name = "detailNotes")
    private String detailNotes;
    public Notes(String titleNotes, String detailNotes) {
        this.titleNotes = titleNotes;
        this.detailNotes = detailNotes;
    }

    public int getIdNotes() {
        return idNotes;
    }

    public void setIdNotes(int idNotes) {
        this.idNotes = idNotes;
    }

    public void setTitleNotes(String titleNotes) {
        this.titleNotes = titleNotes;
    }
    public void setDetailNotes(String detailNotes) {
        this.detailNotes = detailNotes;
    }

    public String getTitleNotes() {
        return this.titleNotes;
    }
    public String getDetailNotes() {
        return this.detailNotes;
    }
}
