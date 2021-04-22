package com.smt.wxdj.swxdj.dao;

import java.util.List;

/**
 * 机构
 */
public class Tenants {


   /**
    * totalCount : 2
    * items : [{"name":"天津克运物流有限公司","editionId":"3fa85f64-5717-4562-b3fc-2c963f66afa6","editionName":null,"id":"39f967cb-0049-d2cd-7099-3bacf49973f9","extraProperties":{}},{"name":"维特联合（北京）科技发展有限公司","editionId":null,"editionName":null,"id":"39fb101f-2ddb-150e-6dfb-03593462b1fd","extraProperties":{}}]
    */

   private int totalCount;
   private List<ItemsBean> items;

   public int getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
   }

   public List<ItemsBean> getItems() {
      return items;
   }

   public void setItems(List<ItemsBean> items) {
      this.items = items;
   }

   public static class ItemsBean {
      /**
       * name : 天津克运物流有限公司
       * editionId : 3fa85f64-5717-4562-b3fc-2c963f66afa6
       * editionName : null
       * id : 39f967cb-0049-d2cd-7099-3bacf49973f9
       * extraProperties : {}
       */

      private String name;
      private String editionId;
      private Object editionName;
      private String id;

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getEditionId() {
         return editionId;
      }

      public void setEditionId(String editionId) {
         this.editionId = editionId;
      }

      public Object getEditionName() {
         return editionName;
      }

      public void setEditionName(Object editionName) {
         this.editionName = editionName;
      }

      public String getId() {
         return id;
      }

      public void setId(String id) {
         this.id = id;
      }
   }
}
