package com.egabi.cjexec.model.baseclasses;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeDef;
import oracle.jbo.JboException;
import oracle.jbo.common.ResourceBundleDef;
import oracle.jbo.common.StringManager;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;

public class EntityEOImpl extends EntityImpl
{
  public EntityEOImpl()
  {
    super();
  }

  public String getLocalizedValue(String key)
  {
    ResourceBundleDef resourceDef = this.getResourceBundleDef();
    Locale locale = ADFContext.getCurrent().getLocale();
    String retVal = StringManager.getLocalizedStringFromResourceDef(resourceDef, key, "No Found", locale, null);
    return retVal;
  }

  public void auditDoDML(int operation, TransactionEvent e)
  {
    System.err.println(">>>>-------------99 "+operation);
    
    try
      {
	
	    String str = "";
	    Object attributeName = null;
	    String checkBundle = null;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    String currentTime = sdf.format(System.currentTimeMillis());
	    AttributeDef[] attributeDefs = this.getEntityDef().getAttributeDefs();
	      if((EntityImpl.DML_UPDATE == operation &&
                  this.getEntityDef().getProperty("AuditUpdate") != null )
                 || (EntityImpl.DML_INSERT == operation &&  this.getEntityDef().getProperty("AuditInsert") != null ) )
	        {
	    Object userFullName = ADFContext.getCurrent().getSessionScope().get("userNameToBC");
	    Object entityId = ADFContext.getCurrent().getSessionScope().get("entityIdToBC");
	    Object userId = ADFContext.getCurrent().getSessionScope().get("userIdToBC");
	    Map<String, Object> flowScope = ADFContext.getCurrent().getPageFlowScope();
	    Object businessCaseId = null;
	    Object folderDataId = null;

//	    System.err.println(">>>> " + getEntityDef().getName() + " :::: " + getEntityDef().getFullName() + "---- " +
//		     getEntityDef().getFullFileName());
	    if(this.getEntityDef().getProperty("TransFolderId") != null)
	      {

		if("EXC_FOLDER_DATA".equalsIgnoreCase(getEntityDef().getSource()))
		  folderDataId = getAttribute("Id");
		else
		  folderDataId = this.getAttribute("TransFolderId") != null? this.getAttribute("TransFolderId"):
				  flowScope.get("IdToHasrBCAudit");
	      }

	    if(flowScope != null)
	      {
		businessCaseId = flowScope.get("IdToBCAudit");

	      }
	    String fromScreen = "Y";
//	    System.out.println(flowScope.get("UpdateParPartNid") + "---->");
	    if(flowScope.get("UpdateParPartNid") != null && flowScope.get("UpdateParPartNid").toString().equals("true"))
	      {
//		System.err.println(getAttribute("BusinessCaseId") + "---->");
		businessCaseId = getAttribute("BusinessCaseId");
		fromScreen = "M";
	      }
	    if(businessCaseId == null)
	      {
		businessCaseId = "";
		fromScreen = "N";
	      }
	    for(AttributeDef attributeDef: attributeDefs)
	      {
	      
		if(isAttributeChanged(attributeDef.getIndex()))
		  { // && attributeDef.getProperty("AuditUpdate") != null
                   
		    Object oldValue = getPostedAttribute(attributeDef.getIndex());
		    Object newValue = getAttribute(attributeDef.getIndex());
                    System.out.println("oldValue IS"+oldValue);
		      System.out.println("newValue IS"+newValue);
		      if(EntityImpl.DML_INSERT == operation &&  this.getEntityDef().getProperty("AuditInsert") != null )
                          oldValue = null;
		    if(attributeDef.getProperty("LookUp") != null)
		      {
			Object tableName = attributeDef.getProperty("LookUp");
			Object displName = attributeDef.getProperty("DisplayAttribute");
			String sql = "SELECT " + displName + "  FROM " + tableName + " Where ID = ";

			PreparedStatement stat;
			ResultSet rs;
			if(oldValue != null)
			  {
			    String oldSql = sql + oldValue;
//			    System.err.println(" oldSql = "+oldSql);
			    stat = getDBTransaction().createPreparedStatement(oldSql, 1);
			    rs = stat.executeQuery();
			    while(rs.next())
			      {
//				System.err.println("rs.getObject(1).toString()  " + rs.getObject(1).toString());
				oldValue = rs.getObject(1);
			      }
			  }
			if(newValue != null)
			  {
			    String newSql = sql + newValue;
			    stat = getDBTransaction().createPreparedStatement(newSql, 1);
//			    System.err.println("newSql = "+newSql);
			    rs = stat.executeQuery();
			    while(rs.next())
			      {
//				System.err.println("rs.getObject(1).toString()  " + rs.getObject(1).toString());
				newValue = rs.getObject(1);
			      }
			  }
		      } else if(attributeDef.getProperty("CheckBox") != null)
		      {
			if( newValue!= null && newValue.equals("Y"))
			  {
			    newValue = "\u0646\u0639\u0645";
			    oldValue = "\u0644\u0627";
			  } else
			  {
			    newValue = "\u0644\u0627";
			    oldValue = "\u0646\u0639\u0645";
			  }
		      }
		    str = "com.egabi.cjexec.model.entities.bcdetails." + getEntityDef().getName() + "." + attributeDef.getName() +
				    "_LABEL";
//		    System.err.println("str == "+str);
		    
		    checkBundle = getLocalizedValue(str);
		    if(checkBundle != null && checkBundle.contains("not found"))
		      {
			attributeName = attributeDef.getName();
		      } else
		      {
			attributeName = checkBundle;
		      }
                    System.err.println(oldValue+" - attributeName - "+attributeName+"-"+newValue);
                    
		    SequenceImpl MySeq = new SequenceImpl("AUDIT_SIMPLE_SEQ", getDBTransaction());
		    String sql = "";
		    Object tableId = getAttribute("Id");
		    oldValue = oldValue == null?null:"'"+oldValue.toString().replaceAll("00:00:00.0", "")+"'";
		    newValue = newValue == null?null:"'"+newValue.toString().replaceAll("00:00:00.0", "")+"'";
		    sql = "Insert into AUDIT_SIMPLE  (ID, TYPE, CREATED_BY, CREATION_DATE, EDITED_ATTRIBUTE,OLD_VALUE, NEW_VALUE, BUSINESS_CASE_ID,FOLDER_DATA_ID,TECHNICAL_NAME,ENTITY_ID,USER_ID,TABLE_ID,FROM_SCREEN) Values ( ";
		    sql += MySeq.getSequenceNumber() + " ,'" + getEntityDef().getSource() + "','" + userFullName + "',TO_DATE('" +
				    currentTime + "','DD/MM/YYYY HH24:MI:SS'),'" + attributeName + "', "+oldValue+" ,"+newValue+",'" +
				    businessCaseId + "'," + folderDataId + ",'" + attributeDef.getColumnName() + "'," + entityId + "," + userId +
				    "," + tableId + ",'" + fromScreen + "')";
		    System.out.println("SQL :********: " + sql);
		    getDBTransaction().executeCommand(sql);
		  }
	      }
	  }
      } catch(SQLException f)
      {
	f.printStackTrace();
      }
  }

  /**
   * Custom DML update/insert/delete logic here.
   * @param operation the operation type
   * @param e the transaction event
   */
  protected void doDML(int operation, TransactionEvent e)
  {
    auditDoDML(operation, e);
    
    super.doDML(operation, e);
  }
  
  public BigDecimal getParentEntityId (BigDecimal entityId){
      PreparedStatement stat = null;
      String value = "0";
      try
        {
          String sql = "SELECT  PARENT_ENTITY_ID  FROM ENTITY Where ID = " + entityId;
      //      System.err.println("==============> getStoclLoc-sql" + sql);
          stat = getDBTransaction().createPreparedStatement(sql, 1);

          ResultSet rs = stat.executeQuery();
          while(rs.next())
            {
      //          System.err.println("rs.getObject(1).toString()  " + rs.getObject(1).toString());
              value = (rs.getObject(1).toString());
            }
        } catch(Exception e)
        {
          e.printStackTrace();
        }
      finally
        {

        }
      BigDecimal parentEntId = new BigDecimal(0);
      try
        {
          parentEntId = new BigDecimal(value);
        } catch(Exception e) {}
      return parentEntId;
  }

  public BigDecimal getentityPresident(BigDecimal entityId)
  {
//    System.err.println("$#####");
    PreparedStatement stat = null;
    String value = "0";
    try
      {
	String sql = "SELECT  ENTITY_PRESIDENT  FROM ENTITY Where ID = " + entityId;
//	System.err.println("==============> getStoclLoc-sql" + sql);
	stat = getDBTransaction().createPreparedStatement(sql, 1);

	ResultSet rs = stat.executeQuery();
	while(rs.next())
	  {
//	    System.err.println("rs.getObject(1).toString()  " + rs.getObject(1).toString());
	    value = (rs.getObject(1).toString());
	  }
      } catch(Exception e)
      {
	e.printStackTrace();
      }
    finally
      {

      }
    BigDecimal seq = new BigDecimal(0);
    try
      {
	seq = new BigDecimal(value);
      } catch(Exception e) {}
    return seq;
  }

  public BigDecimal getSequenceValue(String SequenceName)
  {
    SequenceImpl MySeq = new SequenceImpl(SequenceName, getDBTransaction());
  System.out.println(SequenceName + " ** SequenceName ****  "  );

    return MySeq.getSequenceNumber().getBigDecimalValue();
  }

  public oracle.jbo.domain.Number getMaxSeqValue(String tableName, String columnName)
  {
    oracle.jbo.domain.Number MaxVal = new oracle.jbo.domain.Number(0);
    Object resOfFunction =
      callStoredFunction(Types.NUMERIC, "get_Max_Value_Of_Table(?,?)", new Object[] { tableName, columnName });
    if(resOfFunction != null)
      {
	try
	  {
	    MaxVal = new oracle.jbo.domain.Number(resOfFunction.toString());
	  } catch(SQLException e) {}
      }
    return MaxVal;
  }

  protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars)
  {
    CallableStatement cst = null;
    try
      {
	//Creating sql statement
	cst = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
	//Register dataType for return value
	cst.registerOutParameter(1, sqlReturnType);
	//Pass input parameters value
	if(bindVars != null)
	  {
	    for(int z = 0; z < bindVars.length; z++)
	      {
		cst.setObject(z + 2, bindVars[z]);
	      }
	  }
	cst.executeUpdate();
	//Finally get returned value
	return cst.getObject(1);
      } catch(SQLException e)
      {
	throw new JboException(e.getMessage());
      }
    finally
      {
	if(cst != null)
	  {
	    try
	      {
		cst.close();
	      } catch(SQLException e)
	      {
		e.printStackTrace();
	      }
	  }
      }
  }

  /**
   * Get max value of column + 1
   * @param tableName Table name
   * @param columnName Column name
   * @return oracle.jbo.domain.Number
   */
  public Number getMaxFieldValue(String tableName, String columnName)
  {
    int maxVal = 0;
    try
      {
	String sql = "SELECT NVL(MAX(TO_NUMBER(" + columnName + ")), 0)+1 AS MAX_VAL" + " FROM " + tableName;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    maxVal = rs.getInt("MAX_VAL");
	  }
      } catch(SQLException e)
      {
	e.printStackTrace();
      }

    return new Number(maxVal);
  }

  /**
   * Get max value of column + 1 (where clause (one condition))
   * @param tableName Table name
   * @param columnName Column name
   * @param condColumn Condition column
   * @param CondValue Condition value
   * @return oracle.jbo.domain.Number
   */
  public Number getMaxFieldValue(String tableName, String columnName, String condColumn, String CondValue)
  {
    int maxVal = 0;
    try
      {
	String sql =
		 "SELECT NVL(MAX(TO_NUMBER(" + columnName + ")), 0)+1 AS MAX_VAL" + " FROM " + tableName + " WHERE " +
		 condColumn + " = " + CondValue;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    maxVal = rs.getInt("MAX_VAL");
	  }
      } catch(SQLException e)
      {
	e.printStackTrace();
      }

    return new Number(maxVal);
  }

  /**
   * Get max value of column + 1 (where clause (IS_NULL Condition))
   * @param tableName Table name
   * @param columnName Column name
   * @param condColumn Condition column
   * @return oracle.jbo.domain.Number
   */
  public Number getMaxFieldValue(String tableName, String columnName, String condColumn)
  {
    int maxVal = 0;
    try
      {
	String sql =
		 "SELECT NVL(MAX(TO_NUMBER(" + columnName + ")), 0)+1 AS MAX_VAL" + " FROM " + tableName + " WHERE " +
		 condColumn + " IS NULL";
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    maxVal = rs.getInt("MAX_VAL");
	  }
      } catch(SQLException e)
      {
	e.printStackTrace();
      }

    return new Number(maxVal);
  }

  /**
   * Get max value of column (where clause (one condition))
   * @param tableName Table name
   * @param columnName Column name
   * @param condColumn Condition column
   * @param CondValue Condition value
   * @return String
   */
  public String getMaxFieldString(String tableName, String columnName, String condColumn, String CondValue)
  {
    String maxVal = "";
    try
      {
	String sql =
		 "SELECT NVL(MAX(TO_NUMBER(" + columnName + ")), 0) AS MAX_VAL" + " FROM " + tableName + " WHERE " +
		 condColumn + " = " + CondValue;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    maxVal = rs.getString("MAX_VAL");
	  }
      } catch(SQLException e)
      {
	e.printStackTrace();
      }

    return maxVal;
  }

  /**
   * Get max value of column (where clause (two conditions))
   * @param tableName Table name
   * @param columnName column name
   * @param condColumn condition 1 column
   * @param CondValue condition 1 value
   * @param condColumn1 condition 2 column
   * @param CondValue1 condition 2 value
   * @return String
   */
  public String getMaxFieldString(String tableName, String columnName, String condColumn, String CondValue,
    String condColumn1, String CondValue1)
  {
    String maxVal = "";
    try
      {
	String sql =
		 "SELECT NVL(MAX(TO_NUMBER(" + columnName + ")), 0) AS MAX_VAL" + " FROM " + tableName + " WHERE " +
		 condColumn + " = " + CondValue + " AND " + condColumn1 + " = " + CondValue1;
	;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    maxVal = rs.getString("MAX_VAL");
	  }
      } catch(SQLException e)
      {
	e.printStackTrace();
      }

    return maxVal;
  }

  /**
   * Get Code of parent table
   * @param tableName Parent Table name
   * @param columnName Parent column name
   * @param condColumn Parent condition column
   * @param CondValue Parent condition Value
   * @return String
   */
  public String getParentCodeString(String tableName, String columnName, String condColumn, String CondValue)
  {
    String parentCode = "";
    try
      {
	String sql =
		 "SELECT " + columnName + " AS MAX_VAL" + " FROM " + tableName + " WHERE " + condColumn + " = " +
		 CondValue;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    parentCode = rs.getString("MAX_VAL");
	  }
      } catch(SQLException e) {}
    return parentCode;
  }

  /**
   * Get Code of parent table
   * @param tableName Parent Table name
   * @param columnName Parent column name
   * @param condColumn Parent condition column
   * @param CondValue Parent condition Value
   * @return int
   */
  public int getParentCode(String tableName, String columnName, String condColumn, String CondValue)
  {
    int parentCode = 0;
    try
      {
	String sql =
		 "SELECT " + columnName + " AS MAX_VAL" + " FROM " + tableName + " WHERE " + condColumn + " = " +
		 CondValue;
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    parentCode = rs.getInt("MAX_VAL");
	  }
      } catch(SQLException e) {}
    return parentCode;
  }

  /**
   * Get first day of current year
   * @return oracle.jbo.domain.Date
   */
  public oracle.jbo.domain.Date getFirstDayYear()
  {
    java.util.Date date = new Date(0, 0, 0);
    try
      {
	String sql = "SELECT TRUNC (SYSDATE , 'YEAR') AS DATE_SQL FROM DUAL";
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    date = rs.getDate("DATE_SQL");
	  }
      } catch(SQLException e) {}
    try
      {
	return DateConversionUtils.convertDateFromUtilToJbo(date);
      } catch(SQLException e)
      {
	return null;
      }
  }

  /**
   * Get last day of current year
   * @return oracle.jbo.domain.Date
   */
  public oracle.jbo.domain.Date getLastDayYear()
  {
    java.util.Date date = new Date(0, 0, 0);
    try
      {
	String sql = "SELECT ADD_MONTHS(TRUNC (SYSDATE ,'YEAR'),12)-1 AS DATE_SQL FROM DUAL";
	PreparedStatement pstmt = getDBTransaction().createPreparedStatement(sql, getDBTransaction().DEFAULT);
	ResultSet rs;
	rs = pstmt.executeQuery();
	while(rs.next())
	  {
	    date = rs.getDate("DATE_SQL");
	  }
      } catch(SQLException e) {}
    try
      {
	return DateConversionUtils.convertDateFromUtilToJbo(date);
      } catch(SQLException e)
      {
	return null;
      }
  }
}

