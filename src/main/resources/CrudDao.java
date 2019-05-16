import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CrudDao<T>{

    T get(@Param("id") String id);

    List<T> findAllList(T entity);

    int insert(T entity);

    int update(T entity);

    int delete(T entity);
}