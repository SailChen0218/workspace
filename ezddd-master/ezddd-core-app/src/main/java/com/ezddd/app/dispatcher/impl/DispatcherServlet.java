package com.ezddd.app.dispatcher.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezddd.app.dispatcher.GenericDispatcher;
import com.ezddd.common.response.AppResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 184556141187089270L;

    public static GenericDispatcher getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final GenericDispatcher INSTANCE = new GenericDispatcher();
    }

    public DispatcherServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type","text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        PrintWriter out = response.getWriter();
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        //将json字符串转换为json对象
        JSONObject json = JSONObject.parseObject(sb.toString());

        //需要对其转换
        AppResult appResult = DispatcherServlet.getInstance().doDispatch(json.getInnerMap());

        response.getWriter().print(JSON.toJSONString(appResult));
    }
}
