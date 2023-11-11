import {
  Box,
  Center,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Stack,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import RegisterImage from "../assets/manRunning.jpg";
import Logo from "../assets/Logo.svg";
import { useRouter } from "next/router";
import { useState } from "react";

const Register = () => {
  const router = useRouter();
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [buttonClicked, setButtonClicked] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const fetchData = async () => {
    setError("");
    setErrorMessage("");

    try {
      const response = await fetch("http://localhost:8080/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        //Transforma o corpo da resposta em JSON
        const errorMessage = await response.text();
        setError(errorMessage);
        throw new Error(errorMessage);
      }

      console.log("E-mail e senha enviados para o servidor com sucesso!");
    } catch (error) {
      console.error("Erro ao enviar dados: ", error.message);
    }
  };

  const handleButtonClick = () => {
    if (email && password) {
      fetchData();
    } else {
      setErrorMessage("Por favor, preencha todos os campos.");
    }
    setButtonClicked(true);
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
                placeholder="Digite seu e-mail"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
              />
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
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {error}
                </Text>
              </Box>
            </Box>
            <Box>
              <Button
                bgColor="brand.900"
                w="35vw"
                textColor="white"
                onClick={handleButtonClick}
              >
                Criar conta
              </Button>
              {buttonClicked && errorMessage && (
                <Box py={2}>
                  <Text color="red" fontSize="xs">
                    {errorMessage}
                  </Text>
                </Box>
              )}
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
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Register;
