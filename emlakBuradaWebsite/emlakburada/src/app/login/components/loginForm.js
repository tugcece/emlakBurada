"use client";
import React from "react";
import styles from "../page.module.css";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useRouter, useSearchParams } from "next/navigation";
import { signIn, getSession } from "next-auth/react";
import toast from "react-hot-toast";

const schema = yup
  .object({
    email: yup
      .string()
      .required("Email is required")
      .email("Invalid email format"),
    password: yup
      .string()
      .required("Password is required")
      .min(6, "Password must be at least 6 characters long"),
  })
  .required();

const LoginForm = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const callbackUrl = searchParams.get("callbackUrl") || "/dashboard";

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({ resolver: yupResolver(schema) });

  const onSubmit = async (data) => {
    const result = await signIn("credentials", {
      redirect: false,
      email: data.email,
      password: data.password,
    });

    if (result.error) {
       toast.error("Invalid credentials.");
      console.error("Login error:", result.error);
    } else {
      const session = await getSession();
      const userId = session.user.id;
      router.push(`/user/${userId}`);
    }
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit(onSubmit)}>
      <label>Email</label>
      <input
        className={styles.userInput}
        type="text"
        id="email"
        name="email"
        {...register("email")}
      />
      <p className={styles.errorMessage}>{errors.email?.message}</p>
      <label htmlFor="password">Password</label>
      <input
        className={styles.userInput}
        type="password"
        id="password"
        name="password"
        {...register("password")}
      />
      <p className={styles.errorMessage}>{errors.password?.message}</p>
      <button className={styles.loginButton} type="submit">
        Login →
      </button>
    </form>
  );
};

export default LoginForm;
