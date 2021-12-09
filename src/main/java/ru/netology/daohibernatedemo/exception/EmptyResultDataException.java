package ru.netology.daohibernatedemo.exception;

public class EmptyResultDataException extends RuntimeException {
    public EmptyResultDataException(String msg) {
        super(msg);
    }
}