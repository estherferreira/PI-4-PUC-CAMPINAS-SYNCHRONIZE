import {
  Box,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import RegisterImage from "../assets/manRunning.jpg";
import Logo from "../assets/Logo.svg";
import { useRouter } from "next/router";
import { useState } from "react";
import { useForm, Resolver } from "react-hook-form";
import api from "../api";

type FormValues = {
  email: string;
  password: string;
};

const resolver: Resolver<FormValues> = async (values) => {
  return {
    values: values.email ? values : {},
    errors: !values.password
      ? {
          email: {
            type: "required",
            message: "Digite um email.",
          },
          password: {
            type: "required",
            message: "Digite uma senha.",
          },
        }
      : {},
  };
};

const Register = () => {
  const router = useRouter();
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [buttonClicked, setButtonClicked] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ resolver });

  const onSubmit = (formData: FormValues) => {
    console.log(formData);
    try {
      const response = api.post("/register", {
        email: formData.email,
        password: formData.password,
      });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image
            src={RegisterImage}
            alt="Image"
            layout="fill"
            objectFit="cover"
          />
        </GridItem>
        <GridItem colSpan={4} h="100vh" width="40vw">
          <Flex
            justifyContent="center"
            alignItems="center"
            h="inherit"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <form onSubmit={handleSubmit(onSubmit)}>
              <Box>
                <Text
                  width="450px"
                  fontWeight="inter.400"
                  fontSize="sm"
                  marginLeft="20px"
                  marginBottom="10px"
                >
                  E-mail
                </Text>
                <Input
                  {...register("email", { required: true })}
                  placeholder="Digite seu e-mail"
                  fontFamily="inter.400"
                  fontSize="sm"
                  bgColor="offwhite"
                  borderColor="white"
                  h="48px"
                  w="35vw"
                />
                <Box py={2}>
                  <Text color="red" fontSize="xs">
                    {errors?.email && <>{errors?.email.message}</>}
                  </Text>
                </Box>
              </Box>
              <Box>
                <Text
                  width="450px"
                  fontWeight="inter.400"
                  fontSize="sm"
                  marginLeft="20px"
                  marginBottom="10px"
                >
                  Senha
                </Text>
                <Input
                  {...register("password", { required: true })}
                  type="password"
                  placeholder="Digite sua senha"
                  fontFamily="inter.400"
                  fontSize="sm"
                  bgColor="offwhite"
                  borderColor="white"
                  h="48px"
                  w="35vw"
                />
                <Box py={2}>
                  <Text color="red" fontSize="xs">
                    {errors?.password && <>{errors.password.message}</>}
                  </Text>
                </Box>
              </Box>
              <Box>
                <Button
                  type="submit"
                  bgColor="brand.900"
                  fontFamily="inter.500"
                  w="35vw"
                  textColor="white"
                >
                  Criar conta
                </Button>
              </Box>
              <Divider />
              <Box display="flex" alignItems="center">
                <Text fontWeight="inter.400" fontSize="sm">
                  Ja tenho uma conta
                </Text>
                <Text
                  fontWeight="inter.400"
                  color="brand.900"
                  marginLeft="10px"
                  cursor="pointer"
                  onClick={() => router.push("/login")}
                >
                  Entrar
                </Text>
              </Box>
            </form>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Register;
