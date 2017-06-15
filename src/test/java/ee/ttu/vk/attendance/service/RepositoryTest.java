package ee.ttu.vk.attendance.service;

import ee.ttu.vk.attendance.domain.Teacher;
import ee.ttu.vk.attendance.repository.TeacherRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-config.xml"})
@Transactional
public class RepositoryTest {
    @Inject
    private DataSource dataSource;

    @Inject
    private TeacherRepository teacherRepository;

    @Before
    public void before() throws SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.populate(dataSource.getConnection());
    }

    @Test
    public void findCoursesTest(){
//        teacherRepository.save(new Teacher().setUsername("Admin2").setFullname("Abdul Alhazred").setPassword("12345678").setRole("ADMIN"));
        Assert.assertEquals(2, teacherRepository.count());
    }

}
