package com.aisino;
import java.lang.reflect.Field;
public class FarmartJavaBean{
    private FarmartJavaBean() {}
    public static Object farmartJavaB(Object o, Class< ? > c ){
        // 获取父类，判断是否为相同对象  
        // 获取类中的所有定义字段  
        Field[ ] fields = c.getDeclaredFields( );  
        // 循环遍历字段，获取字段对应的属性值  
        for ( Field field : fields ) {  
            // 如果不为空，设置可见性，然后返回  
            field.setAccessible( true );  
            try {  
                // 设置字段可见，即可用get方法获取属性值。  
                if(field.getType().equals(String.class)){
                    if(field.get( o )==null){
                        field.set(o, "");
                    }
                }
            }catch ( Exception e ) {  
                // System.out.println("error--------"+methodName+".Reason is:"+e.getMessage());  
            }  
        }  
        return o;
    }  
}