package co.com.app.jpa.userapp;

import co.com.app.jpa.userapp.data.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserDataJPARepository extends CrudRepository<UserData/* change for adapter model */, Long>, QueryByExampleExecutor<UserData/* change for adapter model */> {
}
