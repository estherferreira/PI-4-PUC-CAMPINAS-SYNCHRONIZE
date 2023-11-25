import {
  Box,
  Button,
  Flex,
  IconButton,
  Input,
  SimpleGrid,
  Text,
} from "@chakra-ui/react";
import { FiLogOut } from "react-icons/fi";
import { useEffect, useState } from "react";
import { InfoIcon } from "@chakra-ui/icons";
import ProgressBar from "../../components/ProgressBar";
import { useRouter } from "next/router";

const Diagnostic = () => {
  const [newDiagnostic, setNewDiagnostic] = useState(true);
  const [symptoms, setSymptoms] = useState("");
  const [token, setToken] = useState("");
  const router = useRouter();

  useEffect(() => {
    // Acessar localStorage somente no lado do cliente
    const jwtToken = localStorage.getItem("d4pM6FjtykjTwR");
    console.log("Token: ", token);
    if (jwtToken) {
      setToken(jwtToken);
    }
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch(
        `http://localhost:8000/api/diagnosis?userId=000152`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ symptoms }),
        }
      );

      if (!response.ok) {
        //Transforma o corpo da resposta em JSON
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }

      console.log("Sintomas enviados para o servidor com sucesso!");
    } catch (error) {
      console.error("Erro ao enviar dados: ", error.message);
    }
  };

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box display="flex" gap="12px" justifyContent="space-between">
          <Flex align="center" gap="15px">
            <Box
              height="56px"
              width="56px"
              borderRadius="full"
              backgroundColor="black"
            />
            <Box>
              <Text fontFamily="poppins.400" fontSize="lg">
                Sophia Iwara
              </Text>
              <Text
                fontFamily="poppins.400"
                color="gray"
                cursor="pointer"
                onClick={() => {
                  router.push("/profile");
                }}
              >
                Ver perfil
              </Text>
            </Box>
          </Flex>
          <Flex
            align="center"
            gap="10px"
            onClick={() => {
              router.push("/login");
            }}
          >
            <Text fontFamily="poppins.400" cursor="pointer">
              Ir embora
            </Text>
            <FiLogOut />
          </Flex>
        </Box>
        <Box
          marginTop="65px"
          backgroundColor="black"
          height="100px"
          borderRadius="8px"
          padding="20px"
          display="flex"
          justifyContent="space-between"
        >
          <Box>
            <Text color="white" fontFamily="inter.500" marginBottom="10px">
              Um guia para te situar
            </Text>
            <Text color="gray" fontSize="14px" fontFamily="inter.400">
              Descreva o que estiver sentindo e você receberá um relatório
              inteligente e instantâneo para ter uma base do que poderia estar
              ocorrendo.
            </Text>
          </Box>
          <IconButton
            isRound={true}
            variant="solid"
            colorScheme="white"
            aria-label="Done"
            fontSize="20px"
            icon={<InfoIcon />}
          />
        </Box>
        {newDiagnostic ? (
          <>
            <Text
              fontFamily="inter.400"
              fontSize="4xl"
              width="500px"
              marginTop="45px"
            >
              Entao... me diga, o que voce esta sentindo?
            </Text>
            <Input
              borderColor="black"
              borderWidth="2px"
              height="40px"
              width="50%"
              fontSize="14px"
              fontFamily="inter.400"
              placeholder="Dor de cabeça, dores nas costas..."
              marginBottom="15px"
              marginTop="70px"
              value={symptoms}
              onChange={(event) => setSymptoms(event.target.value)}
            />
            <Box>
              <Button
                fontFamily="inter.500"
                color="brand.900"
                backgroundColor="black"
                width="150px"
                onClick={fetchData}
              >
                Enviar
              </Button>
            </Box>
            <Text color="gray" fontSize="sm" width="60%" marginTop="220px">
              * Para obter um diagnóstico preciso e recomendações específicas, é
              importante consultar um médico. Além disso, se os sintomas
              persistirem ou piorarem, é essencial buscar atendimento médico
              imediatamente.
            </Text>
          </>
        ) : (
          <Box display="flex" marginTop="50px" gap="70px">
            <SimpleGrid columns={3} width="50%">
              <ProgressBar percentage={30} height="40vh" reason="Enxaqueca" />
              <ProgressBar
                percentage={50}
                height="40vh"
                reason="Má alimentação"
              />
              <ProgressBar
                percentage={60}
                height="40vh"
                reason="Falta de exercício"
              />
            </SimpleGrid>
            <Box width="50%">
              <Text fontFamily="inter.500" fontSize="2xl">
                Descritivo
              </Text>
              <Text fontFamily="inter.500" marginTop="40px">
                60% - Falta de exercício
              </Text>
              <Text fontFamily="inter.400" marginTop="15px" color="gray">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the.Lorem Ipsum is simply dummy
                text of the printing and typesetting industry. Lorem Ipsum has
                been the.
              </Text>
              <Text fontFamily="inter.500" marginTop="40px">
                60% - Falta de exercício
              </Text>
              <Text fontFamily="inter.400" marginTop="15px" color="gray">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the.Lorem Ipsum is simply dummy
                text of the printing and typesetting industry. Lorem Ipsum has
                been the.
              </Text>
              <Text fontFamily="inter.500" marginTop="40px">
                60% - Falta de exercício
              </Text>
              <Text fontFamily="inter.400" marginTop="15px" color="gray">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the.Lorem Ipsum is simply dummy
                text of the printing and typesetting industry. Lorem Ipsum has
                been the.
              </Text>
            </Box>
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default Diagnostic;
