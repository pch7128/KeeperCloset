package com.pch7128.keepercloset.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pch7128.keepercloset.dto.BoxSizeDTO;
import com.pch7128.keepercloset.dto.StorageDTO;

@Mapper
public interface RvMapper {

	List<BoxSizeDTO> getSizeList();
	List<StorageDTO> getStorageList();
}
