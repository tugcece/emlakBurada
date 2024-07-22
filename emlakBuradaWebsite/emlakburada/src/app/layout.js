import { config } from "@fortawesome/fontawesome-svg-core";
import "@fortawesome/fontawesome-svg-core/styles.css";
import { Inter } from "next/font/google";
import "./globals.css";
import "../../lib/fontawesome";
import { Toaster } from "react-hot-toast";

const inter = Inter({ subsets: ["latin"] });
config.autoAddCss = false;

export const metadata = {
  title: "emlakburada",
  description: "",
};


export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <Toaster position="top-right" />
        {children}
      </body>
    </html>
  );
}
