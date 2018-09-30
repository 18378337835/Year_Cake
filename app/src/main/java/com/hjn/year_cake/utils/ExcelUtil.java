package com.hjn.year_cake.utils;

import android.app.Activity;

import com.hjn.year_cake.model.ExcelCustomer;
import com.hjn.year_cake.model.ExportClientResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import utilpacket.utils.LogUtils;
import utilpacket.utils.SDCardUtils;
import utilpacket.utils.ToastUtils;


/**
 * Created by Year_Cake on 2018/8/28.
 * decription:
 */

public class ExcelUtil {
    public static final String PATH = SDCardUtils.getSDCardPath()+"/DKZX/";

    private WritableWorkbook wwb;
    private String           excelPath;
    private File             excelFile;
    private Activity         activity;

    public ExcelUtil(Activity activity, String path){
        this.activity = activity;
        this.excelPath = path;
        excelFile = new File(PATH, excelPath);
        File fileParent = excelFile.getParentFile();
        LogUtils.e("eeee-->"+fileParent.getAbsolutePath());
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        try{
            excelFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        creatExcel(excelFile);
    }


    /**
     * 创建excel文件
     * 时间   客户类型   姓名   身份证号    手机号   性别    年龄    所在城市  资金需求  期望期限
     */
    public void creatExcel(File file){
        WritableSheet ws = null;

        try{
            if(file.exists()){
                wwb = Workbook.createWorkbook(file);
                ws = wwb.createSheet("客户信息", 0);

                // 在指定单元格插入数据
                Label lbl1  = new Label(0, 0, "时间");
                Label lbl2  = new Label(1, 0, "客户类型");
                Label lbl3  = new Label(2, 0, "姓名");
                Label lbl4  = new Label(3, 0, "身份证号");
                Label lbl5  = new Label(4, 0, "手机号");
                Label lbl6  = new Label(5, 0, "性别");
                Label lbl7  = new Label(6, 0, "年龄");
                Label lbl8  = new Label(7, 0, "所在城市");
                Label lbl9  = new Label(8, 0, "资金需求");
                Label lbl10 = new Label(9, 0, "期望期限");

                ws.addCell(lbl1);
                ws.addCell(lbl2);
                ws.addCell(lbl3);
                ws.addCell(lbl4);
                ws.addCell(lbl5);
                ws.addCell(lbl6);
                ws.addCell(lbl7);
                ws.addCell(lbl8);
                ws.addCell(lbl9);
                ws.addCell(lbl10);
                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeToExcel(List<ExportClientResponse> datas){
        try {
            LogUtils.e("eeee-->"+excelFile.getAbsolutePath());
            Workbook oldWwb = Workbook.getWorkbook(new FileInputStream(excelFile));
            wwb = Workbook.createWorkbook(excelFile, oldWwb);
            WritableSheet ws = wwb.getSheet(0);

            for(int i = 0; i < datas.size(); i++){
                ExportClientResponse excel = datas.get(i);
                LogUtils.e("data--->"+excel.toString());
                Label time = new Label(0, i+1, excel.getCreated_time());
                Label customerType = new Label(1, i+1, excel.getCustomer_type());
                Label name = new Label(2, i+1, excel.getReal_name());
                Label idNo = new Label(3, i+1, excel.getIdentify_no());
                Label mobile = new Label(4, i+1, excel.getMobile());
                Label sex = new Label(5, i+1, excel.getSex());
                Label age = new Label(6, i+1, excel.getAge());
                Label city = new Label(7, i+1, excel.getCity());
                Label loanNeed = new Label(8, i+1, excel.getApply_amount());
                Label loanTime = new Label(9, i+1, excel.getDeadline());

                ws.addCell(time);
                ws.addCell(customerType);
                ws.addCell(name);
                ws.addCell(idNo);
                ws.addCell(mobile);
                ws.addCell(sex);
                ws.addCell(age);
                ws.addCell(city);
                ws.addCell(loanNeed);
                ws.addCell(loanTime );
            }

            wwb.write();
            wwb.close();
            oldWwb.close();
            ToastUtils.showShort(activity, "客户报表导出成功");
            /*修改*/
         // activity.startActivity(new Intent(activity, ExcelListActivity.class));
            LogUtils.e("filePath-->"+excelPath);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("eeee-->"+e.toString()+"     "+e.getMessage());
            ToastUtils.showShort(activity, "客户报表导出失败");
        }
    }

    /**
     * 读取excel内容
     * @param filePath
     * @throws IOException
     * @throws BiffException
     */
    public static List<ExcelCustomer> readExcel(String filePath) throws IOException, BiffException {

        List        list   = new ArrayList();
        InputStream stream = new FileInputStream(filePath);
        Workbook rwb    = Workbook.getWorkbook(stream);
        Sheet sheet  = rwb.getSheet(0);
        if(null != sheet && sheet.getRows() > 0){
            for(int i=0; i<sheet.getRows(); i++){
                String[] str  = new String[sheet.getColumns()];
                Cell cell = null;
                for(int j=0; j<sheet.getColumns(); j++){
                    //获取第i行，第j列的值
                    cell = sheet.getCell(j,i);
                    str[j] = cell.getContents();
                }
                ExcelCustomer customer = new ExcelCustomer();
                customer.setTime(str[0]);
                customer.setCustomerType(str[1]);
                customer.setName(str[2]);
                customer.setIdNo(str[3]);
                customer.setMobile(str[4]);
                customer.setSex(str[5]);
                customer.setAge(str[6]);
                customer.setCity(str[7]);
                customer.setLoanNeed(str[8]);
                customer.setLoanTime(str[9]);

                list.add(customer);
            }
        }

        if(stream != null){
            stream.close();
        }
        if(rwb != null){
            rwb.close();
        }

        return list;

    }


    /**
     * 获取目录下所有的.xls文件
     * @param path
     * @return
     */
    public static List<File> getExcelList(String path){
        List<File> files = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] subfiles = file.listFiles();
            if(null != subfiles && subfiles.length > 0){
                for (File f : subfiles) {
                    if (f.isDirectory()) {
                        getExcelList(f.getAbsolutePath());
                    } else {
                        if(f.getName().endsWith(".xls")){
                            files.add(f);
                        }
                    }
                }
            }
        }
        return files;
    }

    /**
     * 按时间排序获取目录下的.xls文件
     * @param path
     * @return
     */
    public static List<File> listFileGroupTime(String path){
        List<File> files = getExcelList(path);
        if (files != null && files.size() > 0) {
            Collections.sort(files, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;// 最后修改的文件在前
                    } else {
                        return -1;
                    }
                }
            });
        }
        return files;
    }

}
