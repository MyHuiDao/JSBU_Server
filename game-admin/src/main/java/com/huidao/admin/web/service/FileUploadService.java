package com.huidao.admin.web.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.huidao.common.exception.BusinessException;
import com.huidao.common.util.CodeUtil;

public class FileUploadService {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static List<String> uploadManyFile(HttpServletRequest request, String uploadPath, String accpSuffixNmae) {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			List<String> resultList = new ArrayList<String>();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				uoloadFile(resultList, file, uploadPath, accpSuffixNmae);
			}
			if (resultList != null && resultList.size() > 0) {
				return resultList;
			}
		}
		return null;
	}

	private static void uoloadFile(List<String> resultList, MultipartFile file, String uploadPath,
			String accpSuffixNmae) {
		if (file != null) {
			// 取得当前上传文件的文件名称
			String myFileName = file.getOriginalFilename();
			// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
			if (StringUtils.isBlank(myFileName)) {
				throw new BusinessException("文件存在");
			}
			if (StringUtils.isNotBlank(accpSuffixNmae)) {
				String suffix = null;
				int suffixIndex = myFileName.lastIndexOf(".");
				if (suffixIndex == -1) {
					throw new BusinessException("文件类型未知");
				}
				suffix = myFileName.substring(suffixIndex, myFileName.length());
				if (accpSuffixNmae.indexOf(suffix) == -1) {
					throw new BusinessException("文件类型不允许");
				}
			}
			String newFileName = getNewFileName(myFileName);
			// 定义上传路径
			String path = uploadPath + File.separator + newFileName;
			try {
				File localFile = new File(path);
				if (!localFile.exists()) {
					localFile.mkdirs();
				}
				file.transferTo(localFile);
				resultList.add(sdf.format(new Date()) + "\\" + newFileName);
			} catch (IllegalStateException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static String getNewFileName(String myFileName) {
		String suffix = null;
		int suffixIndex = myFileName.lastIndexOf(".");
		if (suffixIndex != -1) {
			suffix = myFileName.substring(suffixIndex, myFileName.length());
		}
		String newFileName = null;
		newFileName = CodeUtil.getRandomUUID();
		if (suffix != null) {
			newFileName += suffix;
		}
		return newFileName;
	}
}
