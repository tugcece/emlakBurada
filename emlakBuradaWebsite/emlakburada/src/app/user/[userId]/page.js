import styles from "./page.module.css";
import Navbar from "../../components/navbar";
import UserDetails from "./components/userDetails";
import PackageDetails from "./components/packageDetails";

 function Packages({ params }) {
   return (
     <main className={styles.main}>
       <Navbar />
       <div className={styles.packageInfo}>
         <UserDetails params={params} />
         <PackageDetails params={params} />
       </div>
     </main>
   );
 }
export default Packages;