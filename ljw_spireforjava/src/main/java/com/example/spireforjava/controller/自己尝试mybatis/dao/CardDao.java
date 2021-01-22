package com.example.spireforjava.controller.自己尝试mybatis.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CardDao {

    @Select("select * from System where id = #{id, jdbcType=LONG} and code= #{code, jdbcType=VARCHAR}")
    String selectSysList(String params);
}
