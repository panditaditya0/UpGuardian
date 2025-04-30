package com.atozdev.uptimemoniter.repository.jobRepositoryy;

import com.atozdev.uptimemoniter.dtos.CommonJobDto;
import com.atozdev.uptimemoniter.entity.Jobs.MainJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainJobRepo extends JpaRepository<MainJobs, Long> {
    MainJobs findByJobTitle(String string);
}
