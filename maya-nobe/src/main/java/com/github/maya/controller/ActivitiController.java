/**
 * COMMERCIAL USE OF THIS SOFTWARE WITHOUT WARRANTY IS NOT ALLOWED.
 * Use is subject to license terms! You can distribute a copy of this software
 * to others for free. This software is to be a non-profit project in the future.
 * All rights reserved! Owned by Stephen Liu.
 */
package com.github.maya.controller;

import org.activiti.engine.RepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ste7en.liu@gmail.com
 * @since 2016/8/31
 */
@Controller
@RequestMapping("/main")
public class ActivitiController {


    @Resource
    private RepositoryService repositoryService;



}
