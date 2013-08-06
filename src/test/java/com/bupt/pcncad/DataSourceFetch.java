package com.bupt.pcncad;

import com.bupt.pcncad.service.IFetchYingjieshengJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.DefaultStyledDocument;

/**
 * Created with IntelliJ IDEA.
 * User: narutolby
 * Date: 13-8-5
 * Time: 下午7:34
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class DataSourceFetch {
    @Autowired
    private IFetchYingjieshengJobService yingjieshengJobService;
    @Test
    public void fetchYingjieshengJobInfo() throws Exception{
       yingjieshengJobService.fetchJobInfo();
    }
}
