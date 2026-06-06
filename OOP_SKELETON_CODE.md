# 🏗️ Skeleton Code: OOP Pillars Implementation

Dokumen ini berisi kerangka kode (skeleton) untuk memenuhi kriteria `RULE.md`. Gunakan kode ini saat tim sudah siap melakukan implementasi nyata.

## 1. DAO Layer (Generic Point)

### `com.ebanking.dao.BaseDAO<T>` (Interface)
```java
package com.ebanking.dao;

import java.util.List;

public interface BaseDAO<T> {
    T getById(Long id);
    List<T> getAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(Long id);
}
```

### `com.ebanking.dao.UserDAO` (Update)
```java
public class UserDAO implements BaseDAO<com.ebanking.model.User> {
    // ... logic existing ...

    @Override public com.ebanking.model.User getById(Long id) { return null; }
    @Override public java.util.List<com.ebanking.model.User> getAll() { return new java.util.ArrayList<>(); }
    @Override public boolean save(com.ebanking.model.User entity) { return false; }
    @Override public boolean update(com.ebanking.model.User entity) { return false; }
    @Override public boolean delete(Long id) { return false; }
}
```

## 2. Service Layer (Abstract & Interface Points)

### `com.ebanking.service.IValidatable` (Interface)
```java
package com.ebanking.service;

public interface IValidatable {
    boolean validate();
}
```

### `com.ebanking.service.BaseTransaction` (Abstract Class)
```java
package com.ebanking.service;

import com.ebanking.model.User;

public abstract class BaseTransaction implements IValidatable {
    protected User user;
    protected double amount;
    protected String description;

    public BaseTransaction(User user, double amount, String description) {
        this.user = user;
        this.amount = amount;
        this.description = description;
    }

    public abstract void execute();

    // Getters & Setters ...
}
```

## 3. Implementation (Inheritance Point)

### `com.ebanking.service.impl.TransferService`
```java
package com.ebanking.service.impl;

import com.ebanking.model.User;
import com.ebanking.service.BaseTransaction;

public class TransferService extends BaseTransaction {
    private String destinationAccount;

    public TransferService(User user, double amount, String description, String destinationAccount) {
        super(user, amount, description);
        this.destinationAccount = destinationAccount;
    }

    @Override
    public boolean validate() { return amount > 0; }

    @Override
    public void execute() {
        if (validate()) { System.out.println("Executing Transfer..."); }
    }
}
```
