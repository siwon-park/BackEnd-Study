package com.example.icc.controller;

import java.io.*;
import java.util.*;

import com.example.icc.entity.Content;
import com.example.icc.repository.ContentRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@RestController
@AllArgsConstructor
@RequestMapping("/content")
public class RestApiController {

    ContentRepository contentRepository;
    
    // 사진 조회
    @GetMapping
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        contentRepository.findTop100ByOrderByUidDesc().forEach( contentList -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("uid", contentList.getUid());
            obj.put("path", contentList.getPath());
            obj.put("title", contentList.getTitle());
            result.add(obj);
        });
        return result;
    }
    
    // 사진 목록 업로드
    @PostMapping
    public Map<String, String> post(@RequestPart("picture") MultipartFile pic, @RequestParam("title") String title, @RequestParam("password") String password) throws IOException {
        String path = System.getProperty("user.dir");
        File file = new File(path + "/src/main/resources/static/" + pic.getOriginalFilename());
        // 받은 파일을 저장하는데, 폴더가 없으면 새로 만든다
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        pic.transferTo(file);
        contentRepository.save(Content.builder()
                .password(password)
                .path(file.getName())
                .title(title)
                .build()).getUid();
        return Map.of("path", file.getName());
    }
    
    // 사진 수정
    @PutMapping("/{uid}")
    public Map<String, String> update(@PathVariable int uid, @RequestPart("picture") MultipartFile pic, @RequestParam("title") String title, @RequestParam("password") String password) throws IOException {
        // content 객체를 가져옴
        Content content = contentRepository.findById(uid).get();
        // 만약 content의 비밀번호와 입력한 비밀번호가 일치하지 않으면
        if (!password.equals(content.getPassword())) {
            return Map.of("403 Forbidden", "Current password is not matching with content password");
        }
        // 일치하면 제목을 바꿈
        content.setTitle(title);
        // 그림이 비어있지 않으면
        if (!pic.isEmpty()) {
            String path = System.getProperty("user.dir");
            File file = new File(path + "/src/main/resources/static/" + pic.getOriginalFilename());
            // 파일 경로가 없으면 생성함
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            pic.transferTo(file);
            contentRepository.save(content);
        }
        return Map.of("path", content.getPath());
    }

    // 사진(글) 삭제
    @DeleteMapping("/{uid}")
    public void delete(@PathVariable int uid, @RequestBody Map<String, Object> body) {
        if (body.get("password").toString().equals(contentRepository.findById(uid).get().getPassword())) {
            contentRepository.deleteById(uid);
        }
    }
}
