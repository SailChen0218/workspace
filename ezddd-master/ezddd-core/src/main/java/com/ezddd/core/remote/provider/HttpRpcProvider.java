//package com.ezddd.common.remote;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import cn.com.jdls.foundation.util.StringUtil;
//import cn.com.jdls.foundation.util.SysUtil;
//import cn.com.servyou.bonde.commons.rpc.BondeServiceInvoker;
//import cn.com.servyou.bonde.dispatch.constants.DispatchCommonConstants;
//import cn.com.servyou.bonde.trade.constants.CommonConstants;
//import cn.com.servyou.bonde.trade.thread.ThreadLocalHolder;
//import cn.com.servyou.bonde.trade.util.JylsxxUtil;
//
//public class RemoteCallServlet extends HttpServlet {
//
//	/**
//	 * serialVersionUID
//	 */
//	private static final long serialVersionUID = 2000015653156597680L;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		doPost(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		doAction(req, resp);
//	}
//
//	private static final Logger LOG = Logger.getLogger(RemoteCallServlet.class);
//
//    protected void doAction(HttpServletRequest req, HttpServletResponse resp) {
//    	long startTime = System.currentTimeMillis();
//
//        String jylsh = req.getParameter(CommonConstants.GLOBAL_PARAM_JYLSH);
//        if (StringUtil.isNullString(jylsh)) {
//            jylsh = JylsxxUtil.getNewJylsh();
//        }
//        ThreadLocalHolder.setJylsh(jylsh);
//
//    	String interfaceName = "";
//        String annotation = "";
//        StringBuilder methodName = new StringBuilder();
//        String params = "";
//        String health = "";
//        String result = "";
//        boolean isClient = false;
//        try {
//        	health = req.getParameter(DispatchCommonConstants.PARAM_RPC_HEALTH);
//
//        	if (StringUtils.isNotBlank(health)) {
//        		//健康检查
//        		result = health + "@" + System.currentTimeMillis();
//        	}
//        	else {
//        		//服务调用
//                InputStream inputStream = req.getInputStream();
//                byte[] inputBytes = SysUtil.readFromStream(inputStream);
//                Map<String, Object> inputMap = (Map<String, Object>) SysUtil.stringToObject(new String(inputBytes, "UTF-8"));
//
//                interfaceName = (String) inputMap.get(DispatchCommonConstants.PARAM_RPC_INTERFACE);
//                methodName = new StringBuilder((String) inputMap.get(DispatchCommonConstants.PARAM_RPC_METHOD));
//                params = (String) inputMap.get(DispatchCommonConstants.PARAM_RPC_PARAMS);
//                annotation = (String) inputMap.get(DispatchCommonConstants.PARAM_RPC_ANNOTATION);
//                boolean fromServiceClient = assertServiceClient(methodName);
//                if (inputMap.get(DispatchCommonConstants.PARAM_RPC_CLIENT)!=null) {
//                	isClient = (Boolean) inputMap.get(DispatchCommonConstants.PARAM_RPC_CLIENT);
//                }
//
//                Map<String, Object> resultMap = BondeServiceInvoker.invokeService(interfaceName, annotation, methodName.toString(), params, isClient);
//
////                if (fromServiceClient) {
////                	resultMap.put("value", JSONObject.toJSON(resultMap.get("value")));
////                }
//                result = SysUtil.objectToString(resultMap);
//        	}
//            OutputStream outputStream = resp.getOutputStream();
//            outputStream.write(result.getBytes("UTF-8"));
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            LOG.error("接口【" + interfaceName + "】远程调用发生异常。", e);
//        } finally {
//        	long wasteTime = System.currentTimeMillis() - startTime;
//			LOG.info("此次服务调用耗时：[" + wasteTime + "]毫秒, 接口名: " + interfaceName + ", 方法名: " + methodName.toString());
//        }
//    }
//
//    private boolean assertServiceClient(StringBuilder methodName) {
//    	int spacePos = methodName.indexOf(" ");
//		if (spacePos != -1) {
//			LOG.debug("调用来自ServiceClient");
//			methodName.delete(0, spacePos + 1);
//			return true;
//		}
//		return false;
//    }
//
//}
