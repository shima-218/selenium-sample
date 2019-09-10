package routeSearch;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;

public class RouteSearch {
    @Test
    public void routeSearch() throws InterruptedException {

      // WebDriverの設定
      System.setProperty("webdriver.chrome.driver", ".\\exe\\chromedriver.exe");
      WebDriver driver = new ChromeDriver();
      driver.manage().window().setSize(new Dimension(650, 768));;

      //変数の設定
      String fromValue = "新羽";//出発地
      String toValue = "豊洲";//到着地
      String yValue = "2019年";
      String mValue = "7月";
      String dValue = "22日";
      String hhValue = "9時";
      String mmValue ="30分";
      String typeValue ="到着";
      String ticketValue ="ICカード優先";
      String expkindValue ="自由席優先";
      String wsValue ="少し急いで";
      String sValue = "到着が早い順";

      //ウェブぺージを開く
      driver.get("https://transit.yahoo.co.jp/");

      //検索条件入力
      //出発地、到着地
      WebElement fromBox = driver.findElement(By.name("from"));
      fromBox.sendKeys(fromValue);
      WebElement toBox = driver.findElement(By.name("to"));
      toBox.sendKeys(toValue);
      //日時
      Select yBox = new Select(driver.findElement(By.name("y")));
      yBox.selectByVisibleText(yValue);
      Select mBox = new Select(driver.findElement(By.name("m")));
      mBox.selectByVisibleText(mValue);
      Select dBox = new Select(driver.findElement(By.name("d")));
      dBox.selectByVisibleText(dValue);
      Select hhBox = new Select(driver.findElement(By.name("hh")));
      hhBox.selectByVisibleText(hhValue);
      Select mmBox = new Select(driver.findElement(By.id("mm")));
      mmBox.selectByVisibleText(mmValue);
      //日時の条件
      WebElement typeRadio = driver.findElement(By.id("tsAvr"));
      if (typeValue.equals("出発")){
    	  typeRadio = driver.findElement(By.id("tsDep"));
      }else if (typeValue.equals("到着")) {
    	  typeRadio = driver.findElement(By.id("tsArr"));
      }else if (typeValue.equals("始発")) {
    	  typeRadio = driver.findElement(By.id("tsFir"));
      }else if (typeValue.equals("終電")) {
    	  typeRadio = driver.findElement(By.id("tsLas"));
      }else{
    	  typeRadio = driver.findElement(By.id("tsAvr"));
      }
      typeRadio.click();
      //運賃、条件
      Select ticketBox = new Select(driver.findElement(By.name("ticket")));
      ticketBox.selectByVisibleText(ticketValue);
      Select expkindBox = new Select(driver.findElement(By.name("expkind")));
      expkindBox.selectByVisibleText(expkindValue);
      Select wsBox = new Select(driver.findElement(By.name("ws")));
      wsBox.selectByVisibleText(wsValue);
      Select sBox = new Select(driver.findElement(By.name("s")));
      sBox.selectByVisibleText(sValue);
      //手段
      WebElement alCheck = driver.findElement(By.name("al"));
      if (alCheck.isSelected()){
    	  alCheck.click();
      }
      WebElement shinCheck = driver.findElement(By.name("shin"));
      if (shinCheck.isSelected()){
    	  shinCheck.click();
      }
      WebElement exCheck = driver.findElement(By.name("ex"));
      if (exCheck.isSelected()){
    	  exCheck.click();
      }
      WebElement hbCheck = driver.findElement(By.name("hb"));
      if (hbCheck.isSelected()){
    	  hbCheck.click();
      }
      WebElement lbCheck = driver.findElement(By.name("lb"));
      if (lbCheck.isSelected()){
    	  lbCheck.click();
      }
      WebElement srCheck = driver.findElement(By.name("sr"));
      if (srCheck.isSelected()){
    	  srCheck.click();
      }

      //検索実行
      WebElement searchButton = driver.findElement(By.id("searchModuleSubmit"));
      searchButton.sendKeys(Keys.ENTER);//

      //所要時間の取得
      String time =driver.findElement(By.className("small")).getText();
      int minutes = stringToMinutes(time);
      System.out.println(time);
      System.out.println(minutes);

      //スクリーンショット保存
      //スクロール
      WebElement element = driver.findElement(By.id("route01"));
      Actions actions = new Actions(driver);
      actions.moveToElement(element);
      actions.perform();
      //スクリーンショット
      File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
      try {
		Files.copy(file, new File("C:\\Users\\shima2019\\Desktop\\Selenium\\SS\\"+fromValue+"から"+toValue+"_"+time+".png"));
      } catch (IOException e) {
		e.printStackTrace();
      }

      driver.quit();
    }


    //〇時間〇分という文字列を、分の数値に変換する
    public int stringToMinutes (String time) {
    	int hour = 0;
    	int minute = 0;
    	if (time.indexOf("時間")==-1) {
    		//分
    		minute = Integer.parseInt(time.substring(0,time.indexOf("分")));
    	}else {
    		//時間
    		hour = Integer.parseInt(time.substring(0, time.indexOf("時間")));
    		//分
    		minute = Integer.parseInt(time.substring(time.indexOf("間")+1,time.indexOf("分")));
    	}
    	//総和
    	int minutesSum = hour*60+minute;
    	return minutesSum;
    }
}
