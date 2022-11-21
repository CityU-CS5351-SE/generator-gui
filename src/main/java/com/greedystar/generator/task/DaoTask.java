package com.greedystar.generator.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.cityu.group6.generator.util.ParameterManager;

import com.greedystar.generator.entity.Constant;
import com.greedystar.generator.invoker.base.AbstractInvoker;
import com.greedystar.generator.task.base.AbstractTask;
import com.greedystar.generator.utils.ConfigUtil;
import com.greedystar.generator.utils.FileUtil;
import com.greedystar.generator.utils.FreemarkerConfigUtil;
import com.greedystar.generator.utils.StringUtil;

import freemarker.template.TemplateException;

/**
 * @author GreedyStar
 * @since 2018/4/20
 */
public class DaoTask extends AbstractTask {

	public DaoTask(AbstractInvoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public void run() throws IOException, TemplateException {
		// 构造Dao填充数据
		Map<String, Object> daoData = new HashMap<>();
		daoData.put("Configuration", ConfigUtil.getConfiguration());
		daoData.put("ClassName", ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER,
				invoker.getClassName()));
		daoData.put("EntityName", StringUtil.firstToLowerCase(invoker.getClassName()));
		daoData.put("DaoClassName",
				ConfigUtil.getConfiguration().getName().getDao().replace(Constant.PLACEHOLDER, invoker.getClassName()));
		// String filePath = FileUtil.getSourcePath() +
		// StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
		// + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getDao());
		
		String filePath = ConfigUtil.getConfiguration().getProjectFolderPath() + "\\"
				+ StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getDao());
		String fileName = ConfigUtil.getConfiguration().getName().getDao().replace(Constant.PLACEHOLDER,
				invoker.getClassName()) + ".java";
		// 生成dao文件
		if (ParameterManager.isGenerate(fileName)) {
			FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_DAO, daoData, filePath, fileName);
		}
	}
}
