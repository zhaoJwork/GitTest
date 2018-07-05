package com.lin;
import com.lin.domain.Group;
import com.lin.domain.User;
import com.lin.domain.UserDetails;
import com.lin.vo.AutoCollectionVo;
import com.lin.vo.UserDetailsVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author yaloo
 */
@SpringBootApplication
@MapperScan("com.lin.mapper")
public class AddressBookApplication {
  @Value("${application.pic_HttpIP}")
  private String picHttpIp;
  public static void main(String[] args){

    SpringApplication.run(AddressBookApplication.class, args);
  }

  @Bean
  public String loadConfig(){
    return UserDetails.picHttpIp = User.picHttpIp = Group.picHttpIp = UserDetailsVo.picHttpIp = AutoCollectionVo.picHttpIp =picHttpIp;
  }
}
