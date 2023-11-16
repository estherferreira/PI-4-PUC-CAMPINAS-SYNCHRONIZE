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
import { IoIosArrowBack } from "react-icons/io";

import { useState, useEffect } from "react";
import { Stomp } from "stompjs/lib/stomp.min.js";
import SockJS from "sockjs-client";

const Register = () => {
  const router = useRouter();
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [buttonClicked, setButtonClicked] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [websocket, setWebsocket] = useState(null);

  useEffect(() => {
    // Estabelece a conexão com o servidor usando SockJS e STOMP
    const socket = new SockJS("http://localhost:8080/websocket");
    const stompClient = Stomp.over(socket);

    console.log(stompClient);

    socket.onerror = function (event) {
      console.error("Erro WebSocket:", event);
    };

    stompClient.connect(
      {},
      () => {
        console.log("Conexão WebSocket estabelecida");
        setWebsocket(stompClient);

        stompClient.subscribe("/topic/userResponses", (message: any) => {
          console.log("Mensagem recebida: ", message.body);
        });

        stompClient.subscribe("/topic/errors", (error: any) => {
          console.error("Erro recebido: ", error.body);
          setError(error.body);
        });
      },
      (error: any) => {
        console.error("Erro na conexão WebSocket: ", error);
        setError("Erro na conexão com o servidor");
      }
    );

    // Fecha a conexão WebSocket quando o componente é desmontado
    return () => {
      if (stompClient && stompClient.connected) {
        stompClient.disconnect();
      }
    };
  }, []);

  const handleRegister = () => {
    setError("");
    setErrorMessage("");

    if (!email || !password) {
      setErrorMessage("Por favor, preencha todos os campos.");
      return;
    }

    if (websocket && websocket.connected) {
      // Envia as credenciais via WebSocket usando STOMP
      const payload = JSON.stringify({ email, password });
      websocket.send("/register", {}, payload);
      console.log("E-mail e senha enviados para o servidor via WebSocket!");
    } else {
      console.error("Erro: WebSocket não está conectado");
      console.log("Erro ao enviar dados");
    }
  };

  const handleButtonClick = () => {
    if (email && password) {
      handleRegister();
    } else {
      setErrorMessage("Por favor, preencha todos os campos.");
    }
    setButtonClicked(true);
  };

  console.log(error);
  console.log(errorMessage);

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
            justifyContent="space-between"
            alignItems="center"
            margin="60px"
            marginTop="200px"
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <Flex
              cursor="pointer"
              alignItems="center"
              gap="10px"
              onClick={() => {
                router.push("/");
              }}
            >
              <IoIosArrowBack />
              <Text fontFamily="poppins.400" sx={{ textAlign: "center" }}>
                Voltar
              </Text>
            </Flex>
          </Flex>
          <Flex
            justifyContent="center"
            alignItems="center"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Box>
              <Text
                width="450px"
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                E-mail
              </Text>
              <Input
                fontFamily="inter.400"
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
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                Senha
              </Text>
              <Input
                fontFamily="inter.400"
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
                fontFamily="inter.400"
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
            <Divider color="gray" />
            <Box display="flex" alignItems="center">
              <Text fontFamily="inter.400" fontSize="sm">
                Ja tenho uma conta
              </Text>
              <Text
                fontFamily="inter.500"
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
