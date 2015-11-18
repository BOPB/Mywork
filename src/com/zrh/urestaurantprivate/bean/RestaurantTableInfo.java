package com.zrh.urestaurantprivate.bean;
/**
 * @copyright 中荣恒科技有限公司
 * @function  餐桌信息类
 * @author 吴强
 * @version v1.1
 * @date 2015-02-05
 */
public class RestaurantTableInfo {
	
	private String TableID;// 桌位id
	private int TableType;// 桌位类型，暂未用到
	private int TableNO;// 桌位号
	private int TableLocationId;// 桌位位置id，表示大厅等的唯一标识码
	private int TableSeatCount;// 桌位数
	private int TableIsFree;// 是否下单，1空闲可以下单，2已被使用，不可下单
	private int TableMergeInfo;// 拆台并台开台标记，-1表示拆台， 数字表示并台id最小的那个坐位，为0或者为空，表示没有拆并台
	private String TableOrderInfo;// 订单信息，拆台后表示如下:A-00001,0002;B-00003,00004;
								// 并台和未拆台表示为:00001,00002,00003,00004;
	private String TableOther;// 其他信息，暂未用到
	private String TableName;// 桌位位置名称
	private boolean  isChecked=false;//这个不是从网络获取的数据，而是自己定义的，方便后面table是否选中的问题
	private int  isBan;//是否被禁台
	
	
	public RestaurantTableInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RestaurantTableInfo(String tableID, int tableType, int tableNO,
			int tableLocationId, int tableSeatCount, int tableIsFree,
			int tableMergeInfo, String tableOrderInfo, String tableOther,
			String tableName, boolean isChecked, int isBan) {
		super();
		TableID = tableID;
		TableType = tableType;
		TableNO = tableNO;
		TableLocationId = tableLocationId;
		TableSeatCount = tableSeatCount;
		TableIsFree = tableIsFree;
		TableMergeInfo = tableMergeInfo;
		TableOrderInfo = tableOrderInfo;
		TableOther = tableOther;
		TableName = tableName;
		this.isChecked = isChecked;
		this.isBan = isBan;
	}
	public String getTableID() {
		return TableID;
	}
	public void setTableID(String tableID) {
		TableID = tableID;
	}
	public int getTableType() {
		return TableType;
	}
	public void setTableType(int tableType) {
		TableType = tableType;
	}
	public int getTableNO() {
		return TableNO;
	}
	public void setTableNO(int tableNO) {
		TableNO = tableNO;
	}
	public int getTableLocationId() {
		return TableLocationId;
	}
	public void setTableLocationId(int tableLocationId) {
		TableLocationId = tableLocationId;
	}
	public int getTableSeatCount() {
		return TableSeatCount;
	}
	public void setTableSeatCount(int tableSeatCount) {
		TableSeatCount = tableSeatCount;
	}
	public int getTableIsFree() {
		return TableIsFree;
	}
	public void setTableIsFree(int tableIsFree) {
		TableIsFree = tableIsFree;
	}
	public int getTableMergeInfo() {
		return TableMergeInfo;
	}
	public void setTableMergeInfo(int tableMergeInfo) {
		TableMergeInfo = tableMergeInfo;
	}
	public String getTableOrderInfo() {
		return TableOrderInfo;
	}
	public void setTableOrderInfo(String tableOrderInfo) {
		TableOrderInfo = tableOrderInfo;
	}
	public String getTableOther() {
		return TableOther;
	}
	public void setTableOther(String tableOther) {
		TableOther = tableOther;
	}
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public int getIsBan() {
		return isBan;
	}
	public void setIsBan(int isBan) {
		this.isBan = isBan;
	}
	
	}


