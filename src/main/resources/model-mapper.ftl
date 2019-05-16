<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${DAO_PACKAGE}.${CLASS_NAME}Dao">
<resultMap id="BaseResultMap" type="${POJO_PACKAGE}.${CLASS_NAME}" >
	<#list FIELDS as FIELD>
		<#if FIELD.column == 'id'>
			<id column="${FIELD.column}" property="${FIELD.property}" jdbcType="${FIELD.jdbcType}" />
		<#else>
			<result column="${FIELD.column}" property="${FIELD.property}" jdbcType="${FIELD.jdbcType}" />
		</#if>
	</#list>
</resultMap>
<sql id="Base_Column_List" >
	<#list FIELDS as FIELD>
		<#if FIELD_index+1 == FIELDS?size>
			${FIELD.column}
		<#else >
			${FIELD.column},
		</#if>
	</#list>
</sql>
<sql id="base_where" >
	<where>
		<#list FIELDS as FIELD>
			<#if FIELD.javaType == 'String'>
				<if test="${FIELD.property} != null and ${FIELD.property} != '' ">
					and ${FIELD.column} = <#noparse>#{</#noparse>${FIELD.property}<#noparse>}</#noparse>
				</if>
			</#if>
		</#list>
	</where>
</sql>
<select id="get" resultMap="BaseResultMap" >
	select		<include refid="Base_Column_List" />
	from ${TABLE_NAME}
	<include refid="base_where"/>
</select>

<select id="findAllList" resultMap="BaseResultMap" >
	select		<include refid="Base_Column_List" />
	from ${TABLE_NAME}
	<include refid="base_where"/>
</select>

<select id="findList" resultMap="BaseResultMap" >
	select		<include refid="Base_Column_List" />
	from ${TABLE_NAME}
	<include refid="base_where"/>
</select>

<insert id="insert" parameterType="${POJO_PACKAGE}.${CLASS_NAME}">
	insert into ${TABLE_NAME}
	(
	<#list FIELDS as FIELD>
		<#if FIELD_index+1 == FIELDS?size>
			${FIELD.column}
		<#else >
			${FIELD.column},
		</#if>
	</#list>
)
values (
	<#list FIELDS as FIELD>
		<#noparse>#{</#noparse>${FIELD.property}<#noparse>}</#noparse>
	</#list>
	)
</insert>

<update id="update" parameterType="${POJO_PACKAGE}.${CLASS_NAME}">
	update ${TABLE_NAME}
	<set>
		<#list FIELDS as FIELD>
			<#if FIELD.javaType == 'String'>
				<if test="${FIELD.property} != null and ${FIELD.property} != '' ">
					${FIELD.column} = <#noparse>#{</#noparse>${FIELD.property}<#noparse>}</#noparse>,
				</if>
			<#else>
				<if test="${FIELD.property} != null">
					${FIELD.column} = <#noparse>#{</#noparse>${FIELD.property}<#noparse>}</#noparse>,
				</if>
			</#if>
		</#list>
	</set>
	<include refid="base_where"/>
</update>

<update id="delete">
	update ${TABLE_NAME}
	set del_flag = '1' where id = <#noparse>#{id}</#noparse>
</update>

</mapper>