        package com.example.Crewpr.service;

        import com.example.Crewpr.entity.Vacation;
        import com.example.Crewpr.entity.VacationHistory;
        import java.time.DayOfWeek;
        import java.time.LocalDate;
        import java.util.List;
        import java.util.Optional;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import com.example.Crewpr.repository.VacationRepository;


        @Service
        public class VacationServiceImp implements VacationService{


        @Autowired
        private VacationRepository vacationRepository;



           

            @Override
            public int TotalVacationDays(int employeeId) {
                int TotalVacationbalance=30-RemainingDays(employeeId);

                return TotalVacationbalance;

            }

            public List<VacationHistory> findVacationHistoryByEmployeeId(int employeeId) {
            return vacationRepository.findVacationHistoryByEmployeeId(employeeId);
        }

        public int getVacationDays(int employeeId) {
            List<Integer> daysList = vacationRepository.findVacationDays(employeeId);
            if (!daysList.isEmpty()) {

                int totalDays = daysList.stream().mapToInt(Integer::intValue).sum();
                return totalDays;
            } else {
                return 30;
            }
        }

            @Override
            public Vacation submitVacation(Vacation vacation) {
              //  if (isVacationValidForSubmission(vacation)) {
                    int vacationDays = countVacationDays(vacation.getStartDate(), vacation.getEndDate());
                    int remainingDays = RemainingDays(vacation.getEmployee().getId());
                    vacation.setVacationDays(vacationDays);
                    vacation.setRemainingDays(remainingDays - vacationDays);
                    vacationRepository.save(vacation);

                    vacationRepository.updateLastRemainingDays(vacation.getEmployee().getId(), vacation.getRemainingDays());
                    return vacation;


        }

            @Override
            public int  RemainingDays(int employeeId) {
                boolean exists = vacationRepository.existsByEmployeeId(employeeId);
                if (exists) {
                    Optional<Integer> remainingDaysOptional = vacationRepository.getLastRemainingDays(employeeId);
                    return remainingDaysOptional.orElse(30);
                } else {
                    return 30;
                }
               
            }


        public int countVacationDays(LocalDate startDate, LocalDate endDate) {
            int count = 0;
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
                if (dayOfWeek != DayOfWeek.FRIDAY && dayOfWeek != DayOfWeek.SATURDAY) {
                    count++;
                }
                currentDate = currentDate.plusDays(1);
            }
            return count;
        }




        public boolean isVacationValid(Vacation vacation) {
            if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
                return false;
            }
            if (vacation.getStartDate().isBefore(LocalDate.now())) {
                return false;
            }
            if (vacation.getStartDate().getDayOfWeek() == DayOfWeek.FRIDAY || vacation.getStartDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
                return false;
            }
            if (vacation.getEndDate().getDayOfWeek() == DayOfWeek.FRIDAY || vacation.getEndDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
                return false;
            }
            return true;
        }



        public boolean isVacationBalanceEnough(Vacation vacation) {
            int totalVacationBalance = RemainingDays(vacation.getEmployee().getId());
            System.out.println(" totalVacationBalance from vacation balance"+totalVacationBalance);
            int vacationDays = countVacationDays(vacation.getStartDate(), vacation.getEndDate());
            System.out.println(" countVacationDays from vacation balance"+vacationDays);
            return totalVacationBalance >= vacationDays;
        }

       /*  public boolean isVacationOverlapping(Vacation vacation) {
            List<VacationHistory> vacationHistory = findVacationHistoryByEmployeeId(vacation.getEmployee().getId());
            for (VacationHistory v : vacationHistory) {
                if (v.getStartDate().isBefore(vacation.getEndDate()) && v.getEndDate().isAfter(vacation.getStartDate())) {
                    return true;
                }
            }
            return false;
        }

        */


        public boolean isVacationValidForSubmission(Vacation vacation) {
            return isVacationValid(vacation) && isVacationBalanceEnough(vacation) && countVacationDays(vacation.getStartDate(), vacation.getEndDate()) > 0;
        }



        }