/**
 * COMMERCIAL USE OF THIS SOFTWARE WITHOUT WARRANTY IS NOT ALLOWED.
 * Use is subject to license terms! You can distribute a copy of this software
 * to others for free. This software is to be a non-profit project in the future.
 * All rights reserved! Owned by Stephen Liu.
 */
package com.github.maya.test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * @author ste7en.liu@gmail.com
 * @since 2016/09/01
 */
public class ActivitiTest extends TestBase {

	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	/**
	 * 部署流程定义，会向如下三张表插入数据
	 * ACT_GE_BYTEARRAY
	 * ACT_GE_PROPERTY
	 * ACT_RE_DEPLOYMENT
	 * ACT_HI_ACTINST
	 * ACT_RU_TASK
	 * ACT_HI_TASKINST
	 * ACT_HI_IDENTITYLINK
	 * ACT_HI_PROCINST
	 * ACT_RU_IDENTITYLINK
	 */
	@Test
	public void deployProcessDefinition() {
		Deployment deployment = repositoryService.createDeployment()
								.name("请假流程")
								.addClasspathResource("diagrams/leave.bpmn")
								.addClasspathResource("diagrams/leave.png")
								.deploy();
		
		System.out.println(String.format("部署id=%s, 部署名称=%s", deployment.getId(), deployment.getName()));
	}
	
	
	/**
	 * 部署流程定义使用zip压缩文件
	 * 如果key相同，版本会升级
	 */
	@Test
	public void deployProcessDefinitionWithZip() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/reimburse.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deployment = repositoryService.createDeployment()
								.name("报销流程")
								.addZipInputStream(zipInputStream)
								.deploy();
		
		System.out.println(String.format("部署id=%s, 部署名称=%s", deployment.getId(), deployment.getName()));
	}
	
	/**
	 * 查询流程定义
	 * 查询ACT_RE_PROCDEF表，查询条件很丰富，可以过滤，排序等
	 */
	@Test
	public void queryProcessDefinitions() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
		//.orderByDeploymentId() //根据流程部署的id排序
		.deploymentId("7501") //根据流程部署的id过滤
		.list(); //返回一个集合列表
		
		list.forEach(e -> 
			System.out.println(String.format("流程定义id=%s, 流程定义名称=%s, 流程定义key=%s, 流程部署id=%s, 资源名称=%s, 资源png名称=%s, 版本=%s",
					e.getId(),
					e.getName(), //对应bpmn文件中name属性值
					e.getKey(),  //对应bpmn文件中id属性值
					e.getDeploymentId(),
					e.getResourceName(),
					e.getDiagramResourceName(),
					e.getVersion())) //当流程定义的key相同，则版本升级
		);
	}
	
	
	/**
	 * 删除流程定义
	 * @throws Exception
	 */
	@Test
	public void deleteProcessDefinition() throws Exception {
		
	}
	
	/**
	 * 查看流程定义图片
	 * @throws Exception
	 */
	@Test
	public void viewProcessDefinitionPic() throws Exception {
		
	}
	
	/**
	 * 启动流程实例
	 * ACT_RU_PROCDEF表里存有流程定义的key
	 * 启动流程实例后，向ACT_RU_EXECUTION表中插入数据
	 */
	@Test
	public void startProcessInstance() {
		String processDefinitionKey = "reimburse";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println(String.format("流程实例id=%s, 流程定义id=%s", 
				processInstance.getId(), 
				processInstance.getProcessDefinitionId()));
	}
	
	/**
	 * 查询个人任务列表
	 */
	@Test
	public void queryPersonalTask() {
		String assignee = "王宝强";
		List<Task> tasks = taskService.createTaskQuery()
							.taskAssignee(assignee)
							.list();
		
		tasks.forEach(e -> 
			System.out.println(String.format("任务id=%s, 任务名称=%s, 任务创建时间=%s, 任务办理人=%s, 流程实例id=%s, 执行对象id=%s, 流程定义id=%s",
					e.getId(), 
					e.getName(), 
					e.getCreateTime(),
					e.getAssignee(),
					e.getProcessInstanceId(),
					e.getExecutionId(),
					e.getProcessDefinitionId()))
		);
	}
	
	/**
	 * 完成任务，任务走到下一个流程
	 */
	@Test
	public void completeTasks() {
		String taskId = "10002";
		String processInstanceId = "";
		taskService.complete(taskId);
		
		taskService.addComment(taskId, processInstanceId, "同意，希望休完假继续拍摄新片");
		
	}
}
