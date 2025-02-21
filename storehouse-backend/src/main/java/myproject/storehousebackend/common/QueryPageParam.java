package myproject.storehousebackend.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class QueryPageParam {
    // default
    private static int PAGE_SIZE = 20;
    private static int PAGE_NUM = 1;

    private int pagesize=PAGE_SIZE;
    private int pagenum=PAGE_NUM;

    private HashMap param=new HashMap();
}
