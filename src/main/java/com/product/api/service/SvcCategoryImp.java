import org.springframework.stereotype.Service;
import com.product.exception.DBAccessException;
import com.product.api.repository.RepoCategory;
import com.product.exception.DBAccessException;

@Service
public class SvcCategory implements SvcCategory{

    final RepoCategory repo;
    SvcCategoryImp(RepoCategory repo){
        this.repo = repo;
    }

    @Override 
    public ResponseEntity<List<Category>>getCategories(){
        try{
            return new ResponseEntity<>(repo.getCategories(),HttpStatus.OK);
        }catch(DataAccessException e){
            throw new DBAccessException(e);
        }
        
    }

    @Override 
    public ResponseEntity<List<Category>> getActiveCategories(){
        return new ResponseEntity(repo.findByStatusOrderByCategory(1),HttpStatus.OK);
    }
}