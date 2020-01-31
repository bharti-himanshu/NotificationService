package com.example.NotificationServiceTry.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="BlackList")
public class BlackList {

    @Id
    @Column(name = "phone_number")
    @NonNull String phoneNumber;

}
