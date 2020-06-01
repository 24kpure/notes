package com.lmj.ruidis.controller;

import com.lmj.ruidis.CommandEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:58 下午 2020/3/8
 **/
public class CommandController {

    private static final Map<String, String> CACHE_MAP = new HashMap<>(1024);

    public static String handle(String command) {
        if (command == null || command.isEmpty() || (!command.startsWith(CommandEnum.GET.toString()) &&
                !command.startsWith(CommandEnum.SET.toString()))) {
            return "command is not valid";
        }
        List<String> commandPart = Arrays.stream(command.split(" ")).filter(e -> !" ".equals(e)).collect(Collectors.toList());
        switch (CommandEnum.valueOf(commandPart.get(0))) {
            case GET:
                return get(commandPart.get(1));
            case SET:
                return set(commandPart.get(1), commandPart.get(2));
            default:
                return "command param is not valid";
        }

    }

    public static synchronized String get(String key) {
        return CACHE_MAP.getOrDefault(key, key + " not exist");
    }

    public static synchronized String set(String key, String value) {
        String result=CACHE_MAP.put(key, value);
        return result==null? value:result;
    }
}