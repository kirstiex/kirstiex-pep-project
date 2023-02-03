public interface AccountDAO{
   public boolean save(Account account);
   public boolean update(Account account);
   public boolean findByAccountNumber(int accountNumber);
   public boolean delete(Account account);

}