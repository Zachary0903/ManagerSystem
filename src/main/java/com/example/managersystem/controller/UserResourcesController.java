package com.example.managersystem.controller;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.example.managersystem.dto.AssignUserResourcesDTO;
import com.example.managersystem.dto.Result;
import com.example.managersystem.enums.RoleEnum;
import com.example.managersystem.service.UserResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserResourcesController {

    @Autowired
    private UserResourcesService userResourcesService;

    @PostMapping("/admin/adduser")
    public Result addUser(@RequestHeader(value = "role") String role, @RequestBody AssignUserResourcesDTO assignUserResourcesDTO) {
        String decryptedRole = Base64Decoder.decodeStr(role);
        if (!RoleEnum.ADMIN.name().toLowerCase().equals(decryptedRole)) {
            return new Result<>().error(HttpStatus.UNAUTHORIZED.value(), "Only admins can assign resources!");
        }
        try {
            userResourcesService.assign(assignUserResourcesDTO.getUserId(), assignUserResourcesDTO.getEndpoint());
        } catch (Exception e) {
            return new Result<>().error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "assign resources failed!");
        }
        return new Result<>().success(assignUserResourcesDTO, "success");
    }

    @GetMapping("/user/{resource}")
    public Result getUserResource(@RequestHeader(value = "userId") Long userId,
                                  @PathVariable(value = "resource") String resourceId) {
        try {
            boolean ownResource = userResourcesService.hasUserResource(userId, resourceId);
            Result<Boolean> result = new Result<>();
            return ownResource ? result.success(ownResource, "access success!")
                    : result.error(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access this resource!");
        } catch (Exception e) {
            return new Result<>().error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "access failed!");
        }

    }

    public static void main(String[] args) {
        System.out.println(Base64Encoder.encode("admin"));
        System.out.println(Base64Encoder.encode("user"));
    }


}
