package dragon3.common;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dragon3.data.Data;
import mine.MineException;
import mine.io.JsonIO;

/**
 * @author k-saito
 */
public class DataList<T extends Data> {

	private List<T> list;

	private Map<String, T> map;

	@SuppressWarnings("unchecked")
	public DataList(String baseDir, List<String> fileList, Class<?> clazz) {
		super();

		list = new ArrayList<T>();
		map = new HashMap<String, T>();
		
		for (String file : fileList) {
			String filePath = baseDir + file;
			try {
				T[] array = (T[])JsonIO.read(filePath, clazz);
				for (T t : array) {
					list.add(t);
					map.put(t.getId(), t);
				}
			} catch (MineException e) {
				throw new RuntimeException("Dataファイルのロードに失敗しました。fileName:[" + filePath + "]", e);
			}
		}
	}

	public List<T> getList() {
		return list;
	}

	public T getData(String id) {
		if (map.containsKey(id)) {
			return map.get(id);
		} else {
			throw new IllegalArgumentException("illegal data id:" + id);
		}
	}

	public Map<String, String> getIdAndName() {
		Map<String, String> idAndName = new LinkedHashMap<String, String>();

		for (int i=0; i<list.size(); i++) {
			Data data = (Data)list.get(i);
			idAndName.put(data.getId(), data.getName());
		}
		return idAndName;
	}
}
