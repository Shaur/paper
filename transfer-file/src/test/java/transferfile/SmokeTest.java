package transferfile;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import transferfile.controller.FileUploadController;
import transferfile.controller.TestController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private TestController controller;

    @Autowired
    private FileUploadController fileUploadController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void uploadControllerTest() throws Exception{
        assertThat(fileUploadController).isNotNull();
    }
}