package com.example.userservice.core.file;

import java.util.List;
import java.util.Map;

public interface AttachmentService {
    /**
     * 파일 목록을 조회한다.
     *
     * @param paramMap 파일 목록 조회에 필요한 파라미터
     * @return 파일 목록
     * @throws Exception
     */
    List<Map<String, Object>> selectFileList(Map<String, Object> paramMap) throws Exception;

    /**
     * 파일을 조회한다.
     *
     * @param paramMap 파일 조회에 필요한 파라미터
     * @return 파일 정보
     * @throws Exception
     */
    Map<String,Object> selectFile(Map<String, Object> paramMap) throws Exception;

    /**
     * 파일을 조회한다.
     *
     * @param paramMap 파일 조회에 필요한 파라미터
     * @return 파일 정보
     * @throws Exception
     */
    List<Map<String, Object>> selectFiles(Map<String, Object> paramMap) throws Exception;

    /**
     * 업로드 단위 업무의 경로를 조회한다.
     *
     * @param paramMap 업로드 단위 업무 조회에 필요한 파라미터
     * @return
     * @throws Exception
     */
    Map<String,Object> selectBizUploadMap(Map<String, Object> paramMap) throws Exception;


    /**
     * 업로드한 파일 정보를 저장한다.
     *
     * @param paramList 저장할 파일정보 목록
     * @throws Exception
     */
    void saveUploadFile_ORI(List<Map<String, Object>> paramList) throws Exception;

    /**
     * 업로드한 파일 정보를 저장한다.
     *
     * @param paramMap 저장할 파일정보
     * @throws Exception
     */
    String saveUploadFile_ORI(Map<String, Object> paramMap) throws Exception;

    /**
     * 업로드한 파일 정보를 저장한다.
     * <pre>
     * ID_ATCH 값 변경
     *   - 기존 20자리 일련번호를 UUID 32자리("-" 제거) 값으로 변경 함(2019-04-12)
     * </pre>
     *
     * @param paramList 저장할 파일정보 목록
     * @throws Exception
     */
    void saveUploadFile(List<Map<String, Object>> paramList) throws Exception;

    /**
     * 업로드한 파일 정보를 저장한다.
     * <pre>
     * ID_ATCH 값 변경
     *   - 기존 20자리 일련번호를 UUID 32자리("-" 제거) 값으로 변경 함(2019-04-12)
     * </pre>
     *
     * @param paramMap 저장할 파일정보
     * @throws Exception
     */
    String saveUploadFile(Map<String, Object> paramMap) throws Exception;

    /**
     * 저장된 파일 마스터 정보 를 복사하여 저장 한다.
     *
     * @param paramMap 저장할 파일정보
     * @throws Exception
     */
    String copySaveFile(Map<String, Object> paramMap) throws Exception;

    /**
     * 저장된 파일 정보 를 복사하여 저장 한다.
     *
     * @param paramMap 저장할 파일정보
     * @throws Exception
     */
    String copySaveDtilFile(Map<String, Object> paramMap) throws Exception;

    /**
     * 업로드 한 파일 정보를 삭제한다.
     *
     * @param paramList 삭제할 파일 정보 목록
     * @throws Exception
     */
    void deleteUploadFile(List<Map<String, Object>> paramList) throws Exception;

    /**
     * 파일 상세 수정 처리
     *
     * @param paramMap
     * @throws Exception
     */
    void updateUploadFile(Map<String, Object> paramMap) throws Exception;


    /**
     * 다중의 파일 정보를 조회한다.
     *
     * @param filesMap 파일 정보를 조회하기 위해 필요한 파라미터
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectFilesInfo(Map<String, Object> filesMap) throws Exception;

    /**
     * 단일 파일 마스터 정보를 조회한다.
     *
     * @param filesMap 파일 정보를 조회하기 위해 필요한 파라미터
     * @return
     * @throws Exception
     */
    int selectOneCntFileMaster(Map<String, Object> filesMap) throws Exception;

    /**
     * 시스템의 업로드 루트 경로를 조회한다.
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    Map<String, Object> selectOneUploadRootPath(Map<String, Object> paramMap) throws Exception;

    Map<String, Object> selectOneUploadRootPathServerType(Map<String, Object> paramMap) throws Exception;
    /**
     * 시스템의 템플릿 파일 경로를 조회한다.
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    Map<String, Object> selectOneTemplateUploadPath(Map<String, Object> paramMap) throws Exception;

    /**
     * 파일 다운로드 링크 목록 조회
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> selectFileLinkList(Map<String, Object> paramMap) throws Exception;

    /**
     * 파일 링크 만들기
     *
     * @param reqModel
     * @return
     * @throws Exception
     */
    Map<String, Object> makeFileLink(Map<String, Object> reqModel) throws Exception;
}
