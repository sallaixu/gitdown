package down.sallai.ml.services;


import com.sun.management.OperatingSystemMXBean;
import down.sallai.ml.bean.SystemInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;


@Service
public class MonitorService {
      private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
      /*
      * 返回文件管理所有信息（实时更新部分）
      *
      * */
      public SystemInfo getAllSystemInfo(String fileLocal){
          int cpuLoad = this.getCpuLoad();
          Map memeryLoad = getMemeryLoad();
          Map spaceInfo = getSpaceInfo(fileLocal);
          int fileNumber = getFileNumber(fileLocal);
          SystemInfo systemInfo = new SystemInfo();
          systemInfo.setCpu(cpuLoad);
          systemInfo.setMemery(memeryLoad);
          systemInfo.setFileNumber(fileNumber);
          systemInfo.setSpace(spaceInfo);
          return systemInfo;

      }
      /*
      * 获取cpu负载情况
      *
      * */
      public int getCpuLoad(){
          double cpuLoad = osmxb.getSystemCpuLoad();
          int percentCpuLoad = (int) (cpuLoad * 100);
          return percentCpuLoad;
      }

      /*
      *获取内存信息
      *
      * */

      public Map getMemeryLoad(){

          double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
          double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

          double value = freePhysicalMemorySize/totalvirtualMemory;
          int percentMemoryLoad = (int) ((1-value)*100);
          HashMap<String, Object> info = new HashMap<>();
          info.put("total",totalvirtualMemory);
          info.put("free",freePhysicalMemorySize);
          info.put("percent",percentMemoryLoad);
          return info;

      }
      /*
      * 获取文件数
      *
      * */
      public int getFileNumber(String fileLocal){

          File file = new File(fileLocal);
          if(!file.isDirectory()) return -1;
          File[] files = file.listFiles();
          return files.length;
      }
      /*
      * 获取磁盘空间
      * */
     public Map getSpaceInfo(String fileLocal){
         HashMap<String, Object> info = new HashMap<>();
         File file = new File(fileLocal);
         float freeSpace = (file.getFreeSpace()/1024/1024/1024);
         float totalSpace = (file.getTotalSpace()/1024/1024/1024);
         freeSpace = Float.parseFloat(String.format("%.1f", freeSpace));
         totalSpace = Float.parseFloat(String.format("%.1f", totalSpace));
         info.put("total",totalSpace);
         info.put("free",freeSpace);
         info.put("percent",Math.ceil((1-(freeSpace/totalSpace))*100));
         return info;

     }

}
