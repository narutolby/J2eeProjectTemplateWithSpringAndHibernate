package com.bupt.pcncad.service;

import com.bupt.pcncad.dao.yingjiesheng.IYingjieshengDao;
import com.bupt.pcncad.domain.EmployInfoModel;
import javassist.compiler.ast.StringL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: narutolby
 * Date: 13-8-6
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FetchYingjieshengJobServiceImpl implements IFetchYingjieshengJobService {

   @Autowired
   private IYingjieshengDao yingjieshengDao;
   private String rootUrl = "http://my.yingjiesheng.com/ajax.php?jsrandid=60244&action=search&option=location&info=beijing_FullTime&page=";
   private int pageNum = 150;
   private Pattern patternTr = Pattern.compile("<tr class.*?>(.*?)</tr>");
   private Pattern patternTd = Pattern.compile("<td.*?>(.*?)</td>");
   private Pattern patternLink = Pattern.compile("<a href=\"([\\s\\S]*?)\"[\\s\\S]*?>(.*?)</a>");
   private String yingjieshengHomePage = "http://my.yingjiesheng.com";
    @Override
    public void fetchJobInfo() throws Exception {
         String url = "";
         String html = "";
         for(int i=1;i<=pageNum;i++){
            url = this.rootUrl+i;
            html = this.requestPage(url);
            Matcher matcherTr = patternTr.matcher(html);
            System.out.println("===========================================================");
            while(matcherTr.find()){
                EmployInfoModel employInfoModel = new EmployInfoModel();
                String tr = matcherTr.group(1);
                Matcher matcherTd = patternTd.matcher(tr);
                int count = 0;
                while(matcherTd.find()){
                    count++;
                    String td = matcherTd.group(1);
                    if(count==1 || count==2){
                       Matcher matcherLink = patternLink.matcher(td);
                       if(count==1){
                           while(matcherLink.find()){
                               employInfoModel.setCompanyLink(this.yingjieshengHomePage + matcherLink.group(1));
                               employInfoModel.setCompanyName(matcherLink.group(2));
                           }
                       }else{
                           while(matcherLink.find()){
                               employInfoModel.setJobLink(this.yingjieshengHomePage + matcherLink.group(1));
                               employInfoModel.setJobName(matcherLink.group(2));
                           }
                       }
                    }else if(count==3){
                        employInfoModel.setEmploy_num(td.equals("-")?0:Integer.valueOf(td));
                    }else if(count==4){
                        employInfoModel.setJobType(td);
                    }else if(count==5){
                        employInfoModel.setJobCity(td);
                    }else{
                        employInfoModel.setPubDate(td);
                    }
                }
                this.yingjieshengDao.save(employInfoModel);
                System.out.println(employInfoModel);
            }
             System.out.println("===========================================================");
             Thread.sleep(1000);
         }
    }


    private String requestPage(String link) throws Exception{
        URL url = new URL(link);
        HttpURLConnection request = (HttpURLConnection)url.openConnection();
        request.setRequestMethod("GET");
        request.connect();
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"gbk"));
        String line;
        while((line = bufferdReader.readLine())!=null){
           sb.append(line.trim());
        }
       return sb.toString();
    }
}
