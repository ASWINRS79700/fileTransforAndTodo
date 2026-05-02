package com.project.todo.controller;

import com.project.db.service.MongoService;
import com.project.entity.ToDo;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/todo/v1")
public class TodoController {
    @Autowired
    MongoService mongoService;
    @PostMapping("/add")
    public ToDo Todo(@RequestBody ToDo toDo){
        LocalDateTime localDateTime = LocalDateTime.now();

        Date date = Date.from(
                localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        );
        toDo.setCreatedTime(date);
        toDo.setUuid(UUID.randomUUID().toString());
       mongoService.save(toDo,ToDo.class);
        System.out.println("created new todo");
       return toDo;
    }


    @GetMapping("getAll")
    public List<ToDo> getAll(){
        System.out.println("found all todo");
        return mongoService.get(ToDo.class,"aswin");
    }

    @DeleteMapping("delete")
    public String delete(@RequestHeader(value = "uid",required = true)String uid ){
        mongoService.delete(ToDo.class,"aswin",uid);
        System.out.println("deleted todo of uuid : "+uid);
        return "deleted";
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "File is empty";
        }

        try {
            // ✅ Absolute path (FIX)
            String uploadDir = "C:/Users/aswin/deep/todo/todo/uploads";

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // create directory
            }

            // ✅ unique filename
            String fileName = file.getOriginalFilename();

            File dest = new File(uploadDir+"/" + fileName);

            file.transferTo(dest);

            return "Uploaded: " + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }
}
