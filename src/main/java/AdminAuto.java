public class AdminAuto implements Runnable{
    public Admin form;
    public AdminAuto(Admin admin){
        form=admin;
    }

    @Override
    public void run() {
        while(true) {
            try {
                java.awt.event.ActionEvent evt =null;
                form.jButton1ActionPerformed(evt);
                form.jButton2ActionPerformed(evt);
                form.jButton3ActionPerformed(evt);
                form.jButton4ActionPerformed(evt);
                form.jButton5ActionPerformed(evt);
                form.jButton6ActionPerformed(evt);
                Thread.sleep(10000);
            }
            catch(InterruptedException e){

            }
        }
    }
}
