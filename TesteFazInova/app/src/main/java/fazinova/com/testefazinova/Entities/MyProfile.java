package fazinova.com.testefazinova.Entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import lombok.Data;

/**
 * Created by jundisassaki on 6/23/15.
 */

@Data
@Table(name = "MyProfile")
public class MyProfile extends Model {


    @Column(name = "UserId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String userId;


    @Column(name = "Name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String name;


    @Column(name = "Email", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String email;


    @Column(name = "ThumbUrl", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String thumbUrl;

}
